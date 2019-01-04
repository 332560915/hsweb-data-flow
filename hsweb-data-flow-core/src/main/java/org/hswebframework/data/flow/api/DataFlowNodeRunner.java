package org.hswebframework.data.flow.api;

public interface DataFlowNodeRunner {

    String getId();

    String getRunnerId();

    String getNodeId();

    String getNodeName();

    void start(DataFlowNodeContext context);

    void stop();

}
