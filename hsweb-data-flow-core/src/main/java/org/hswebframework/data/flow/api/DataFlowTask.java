package org.hswebframework.data.flow.api;



public interface DataFlowTask {

    String getId();

    String getModelName();

    String getModelId();

    long getCreateTime();

    long getStartTime();

    void start(DataFlowContext context);

    void stop();

    void pause();

}
