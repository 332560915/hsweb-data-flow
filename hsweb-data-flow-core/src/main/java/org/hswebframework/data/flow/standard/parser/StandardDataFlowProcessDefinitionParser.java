package org.hswebframework.data.flow.standard.parser;

import lombok.Getter;
import lombok.Setter;
import org.hswebframework.data.flow.api.DataFlowProcessDefinitionParser;
import org.hswebframework.data.flow.model.DataFlowProcessDefinition;
import org.hswebframework.data.flow.model.PersistentDataFlowProcessDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouhao
 * @since 1.0.0
 */
public class StandardDataFlowProcessDefinitionParser implements DataFlowProcessDefinitionParser {

    @Getter
    @Setter
    private List<DataFlowProcessDefinitionParserStrategy> strategies = new ArrayList<>();

    @Override
    public DataFlowProcessDefinition parse(PersistentDataFlowProcessDefinition persistent) {
        DataFlowProcessDefinition definition = strategies
                .stream()
                .filter(strategy -> strategy.support(persistent.getMetaFormat()))
                .map(strategy -> strategy.parse(persistent.getMeta()))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("不支持的流程定义格式:" + persistent.getMetaFormat()));
        definition.setId(persistent.getDefinitionId());
        definition.setName(persistent.getName());
        definition.setVersion(persistent.getVersion());
        return definition;
    }
}
