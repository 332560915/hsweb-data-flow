package org.hswebframework.data.flow.api;


public interface TaskFuture<T> {

    boolean success();

    Throwable cause();

    T get();

    static <T> TaskFuture<T> success(T result) {
        SimpleTaskFuture<T> future = new SimpleTaskFuture<>();
        future.setSuccess(true);
        future.setResult(result);
        return future;
    }

    static <T> TaskFuture<T> fail(Throwable cause) {
        SimpleTaskFuture<T> future = new SimpleTaskFuture<>();
        future.setSuccess(false);
        future.setCause(cause);
        return future;
    }
}
