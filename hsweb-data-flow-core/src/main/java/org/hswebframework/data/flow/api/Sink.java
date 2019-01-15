package org.hswebframework.data.flow.api;

/**
 * @author zhouhao
 * @since 1.0.0
 */
public interface Sink<T> {
    void write(T data);

    void close();

    boolean isClose();
}
