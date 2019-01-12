package org.hswebframework.data.flow.api;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public interface RunnerFuture<T> {

    boolean success();

    Throwable cause();

    T get();

    T get(long timeout, TimeUnit timeUnit);

    String getRunnerId();

    String getNodeId();

}
