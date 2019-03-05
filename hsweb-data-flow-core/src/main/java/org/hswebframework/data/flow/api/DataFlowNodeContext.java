package org.hswebframework.data.flow.api;

import java.util.Optional;

public interface DataFlowNodeContext {

    <T> Optional<T> getPreNodeResult(Class<T> type);

    Logger logger();

    Progress progress();

    boolean isSuccess();

    void success(Object result);

    void error(Throwable msg);

}
