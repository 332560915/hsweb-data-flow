package org.hswebframework.data.flow.api;

import org.hswebframework.data.flow.model.DataFlowProcessDefinition;
import org.hswebframework.data.flow.model.PersistentDataFlowProcessDefinition;

/**
 * @author zhouhao
 * @since 1.0.0
 */
public interface DataFlowProcessDefinitionParser {
    DataFlowProcessDefinition parse(PersistentDataFlowProcessDefinition persistent);
}
