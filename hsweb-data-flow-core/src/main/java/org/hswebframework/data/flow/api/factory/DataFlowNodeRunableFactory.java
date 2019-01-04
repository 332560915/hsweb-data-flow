package org.hswebframework.data.flow.api.factory;

import org.hswebframework.data.flow.model.DataFlowNode;

public interface DataFlowNodeRunableFactory {

    Runnable create(DataFlowNode node);

}
