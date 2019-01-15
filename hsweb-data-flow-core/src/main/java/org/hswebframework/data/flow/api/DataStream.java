package org.hswebframework.data.flow.api;

import java.io.Closeable;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author zhouhao
 * @since 1.0.0
 */
public interface DataStream<T> extends Closeable {

    DataStream<T> filter(Predicate<T> predicate);

    <R> DataStream<R> map(Function<T, R> mapping);

    DataStream<List<T>> buffer(int bufferSize);

    void forEach(Consumer<T> consumer);

}
