package org.hswebframework.data.flow.api;

import java.util.Map;
import java.util.Optional;

public interface DataFlowContext {

    Map<String, Object> getParameters();

    @SuppressWarnings("all")
    default <T> Optional<T> getParameter(String parameterName) {
        return Optional.ofNullable(getParameters())
                .map(map -> (T) map.get(parameterName));
    }

    <T> Optional<T> getNodeResult(String nodeId, Class<T> type);

    void putParameter(String key, Object value);

    Logger logger();

    Progress progress();

    boolean isStream();

}
