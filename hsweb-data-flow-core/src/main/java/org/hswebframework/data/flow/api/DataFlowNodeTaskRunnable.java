package org.hswebframework.data.flow.api;

import java.io.Closeable;
import java.util.function.Consumer;

/**
 * @author zhouhao
 * @since 1.0.0
 */
public interface DataFlowNodeTaskRunnable {

    String getRunnerId();

    Closeable run(DataFlowNodeContext context, Consumer<TaskFuture<Object>> resultConsumer) throws Exception;

    void stop();

    static DataFlowNodeTaskRunnable doNoting() {
        return new DataFlowNodeTaskRunnable() {
            @Override
            public String getRunnerId() {
                return "none";
            }

            @Override
            public Closeable run(DataFlowNodeContext context, Consumer<TaskFuture<Object>> resultConsumer) throws Exception {
                resultConsumer.accept(TaskFuture.success(null));
                return ()->{};
            }

            @Override
            public void stop() {

            }
        };
    }
}
