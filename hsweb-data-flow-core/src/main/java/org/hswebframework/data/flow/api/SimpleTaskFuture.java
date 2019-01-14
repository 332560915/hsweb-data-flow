package org.hswebframework.data.flow.api;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhouhao
 * @since 1.0.0
 */
@Getter
@Setter
public class SimpleTaskFuture<T> implements TaskFuture<T> {
    private boolean success;

    private Throwable cause;

    private T result;

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public Throwable cause() {
        return cause;
    }

    @Override
    public T get() {
        return result;
    }


}
