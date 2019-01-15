package org.hswebframework.data.flow.cluster;

import java.util.function.Consumer;

/**
 * @author zhouhao
 * @since 1.0.0
 */
public interface Queue<T> {
    T take();

    void accept(Consumer<T> consumer);

    void add(T data);

    void close();

    void onClose(Runnable runnable);
}
