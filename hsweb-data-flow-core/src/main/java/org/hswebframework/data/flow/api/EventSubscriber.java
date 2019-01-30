package org.hswebframework.data.flow.api;

import java.util.function.Consumer;

/**
 * @author zhouhao
 * @since 1.0.0
 */
public interface EventSubscriber {
    <E> long subscribe(Class<E> type, Consumer<E> eventConsumer);

    void describe(Consumer<?> consumer);

}
