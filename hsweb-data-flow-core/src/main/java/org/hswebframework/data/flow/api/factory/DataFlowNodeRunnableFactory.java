package org.hswebframework.data.flow.api.factory;

import org.hswebframework.data.flow.api.DataFlowNodeTaskRunnable;
import org.hswebframework.data.flow.model.DataFlowTaskDefinition;
import org.hswebframework.data.flow.model.ExecutableDefinition;

public interface DataFlowNodeRunnableFactory {

    DataFlowNodeTaskRunnable create(ExecutableDefinition node);

}
