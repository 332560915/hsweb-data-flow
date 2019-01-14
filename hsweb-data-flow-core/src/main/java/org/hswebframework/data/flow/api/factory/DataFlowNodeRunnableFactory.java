package org.hswebframework.data.flow.api.factory;

import org.hswebframework.data.flow.api.DataFlowNodeTaskRunnable;
import org.hswebframework.data.flow.model.DataFlowTaskDefinition;

public interface DataFlowNodeRunnableFactory {

    DataFlowNodeTaskRunnable create(DataFlowTaskDefinition node);

}
