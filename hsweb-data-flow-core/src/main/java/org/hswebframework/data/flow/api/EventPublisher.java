package org.hswebframework.data.flow.api;

/**
 * @author zhouhao
 * @since 1.0.0
 */
public interface EventPublisher {
    void publish(Object publish);

    <E> void publish(Class<? super E> type, E event);
}
