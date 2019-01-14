package org.hswebframework.data.flow.api;


import java.util.Map;

public interface DataFlowProcess {

    String getId();

    long getCreateTime();

    long getStartTime();

    DataFlowContext newContext(Map<String, Object> parameter);

    void start(DataFlowContext context);

    void stop();

    void pause();

}
