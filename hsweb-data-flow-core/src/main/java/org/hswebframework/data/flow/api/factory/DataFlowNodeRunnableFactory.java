package org.hswebframework.data.flow.api.factory;

import org.hswebframework.data.flow.model.DataFlowNode;

public interface DataFlowNodeRunnableFactory {

    Runnable create(DataFlowNode node);

}
