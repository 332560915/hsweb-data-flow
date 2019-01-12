package org.hswebframework.data.flow.api;

import java.util.function.Consumer;

public interface DataFlowNodeTask {

    String getId();

    String getRunnerId();

    String getNodeId();

    String getNodeName();

    void start(DataFlowNodeContext context, Consumer<RunnerFuture<Object>> resultHandle);

    void stop();

}
