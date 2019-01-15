package org.hswebframework.data.flow.standard;

import lombok.Getter;
import lombok.Setter;
import org.hswebframework.data.flow.api.DataFlowContext;
import org.hswebframework.data.flow.api.DataFlowProcessDefinitionParser;
import org.hswebframework.data.flow.api.DataFlowService;
import org.hswebframework.data.flow.api.factory.DataFlowNodeRunnableFactory;
import org.hswebframework.data.flow.model.DataFlowProcessDefinition;
import org.hswebframework.data.flow.model.PersistentDataFlowProcessDefinition;
import org.hswebframework.data.flow.repository.DataFlowProcessDefinitionRepository;
import org.hswebframework.data.flow.utils.IdUtils;

import java.util.Map;
import java.util.Objects;

/**
 * @author zhouhao
 * @since 1.0.0
 */
@Getter
@Setter
public class StandardDataFlowService implements DataFlowService {

    private DataFlowNodeRunnableFactory runnableFactory;

    private DataFlowProcessDefinitionParser definitionParser;

    private DataFlowProcessDefinitionRepository processRepository;

    @Override
    public String startDataFlow(String processDefinitionId, Map<String, Object> parameter) {
        PersistentDataFlowProcessDefinition persistentModel = processRepository.findById(processDefinitionId);
        Objects.requireNonNull(persistentModel, "流程不存在");
        DataFlowProcessDefinition definition = definitionParser.parse(persistentModel);
        return startDataFlow(definition, parameter);
    }

    public String startDataFlow(DataFlowProcessDefinition definition, Map<String, Object> parameter) {
        StandardDataFlowProcess process = new StandardDataFlowProcess();
        process.setId(IdUtils.newUUID());
        process.setCreateTime(System.currentTimeMillis());
        process.setRunnableFactory(runnableFactory);
        process.setDefinition(definition);
        DataFlowContext context = process.newContext(parameter);
        process.start(context);
        return process.getId();
    }
}
