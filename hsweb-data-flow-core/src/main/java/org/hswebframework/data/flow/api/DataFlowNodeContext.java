package org.hswebframework.data.flow.api;

import java.util.Optional;

public interface DataFlowNodeContext {

    DataFlowContext getFlowContext();

    <T> Optional<T> getPreNodeResult(Class<T> type);

    String getPreNodeType();

    String getPreNodeName();

    String getPreNodeId();

    Logger logger();

    Progress progress();

}
