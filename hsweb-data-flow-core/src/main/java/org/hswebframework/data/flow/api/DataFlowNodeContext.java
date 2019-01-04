package org.hswebframework.data.flow.api;

import java.util.Map;
import java.util.Optional;

public interface DataFlowNodeContext {

    DataFlowContext getFlowContext();

    Map<String, Object> getParameters();

    @SuppressWarnings("all")
    default <T> Optional<T> getParameter(String parameterName) {
        return Optional.ofNullable(getParameters())
                .map(map -> (T) map.get(parameterName));
    }

    <T> Optional<T> getPreNodeResult(Class<T> type);

    String getPreNodeType();

    String getPreNodeName();

    String getPreNodeId();

    Logger logger();

    Progress progress();

}
