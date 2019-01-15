package org.hswebframework.data.flow.cluster;

import java.util.function.Consumer;

/**
 * @author zhouhao
 * @since 1.0.0
 */
public interface Topic<T> {

    void onMessage(Consumer<T> consumer);

}
