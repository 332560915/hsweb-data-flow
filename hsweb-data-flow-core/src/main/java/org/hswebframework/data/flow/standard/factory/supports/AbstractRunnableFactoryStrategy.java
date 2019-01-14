package org.hswebframework.data.flow.standard.factory.supports;

import lombok.Getter;
import lombok.Setter;
import org.hswebframework.data.flow.api.DataFlowNodeContext;
import org.hswebframework.data.flow.api.DataFlowNodeTaskRunnable;
import org.hswebframework.data.flow.api.TaskFuture;
import org.hswebframework.data.flow.api.TaskRunner;
import org.hswebframework.data.flow.standard.factory.DataFlowNodeRunnableFactoryStrategy;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * @author zhouhao
 * @since 1.0.0
 */
@Getter
@Setter
public abstract class AbstractRunnableFactoryStrategy<C> implements DataFlowNodeRunnableFactoryStrategy<C> {

    private TaskRunner runner;

    public AbstractRunnableFactoryStrategy(TaskRunner runner) {
        Objects.requireNonNull(runner);
        this.runner = runner;
    }

    public abstract String getName();

    public abstract TaskRunnable createRunnable(C config);

    @Override
    public DataFlowNodeTaskRunnable create(C config) {
        TaskRunnable runnable = createRunnable(config);
        return new DataFlowNodeTaskRunnable() {
            AtomicReference<String> runId = new AtomicReference<>();

            @Override
            public String getRunnerId() {
                return runner.getRunnerId();
            }

            @Override
            public void run(DataFlowNodeContext context, Consumer<TaskFuture<Object>> resultConsumer) {
                try {
                    runId.set(runner.run(() -> {
                        try {
                            runnable.run(context, resultConsumer);
                        } catch (Throwable e) {
                            context.logger().error("执行[{}]失败.", getName(), e);
                            resultConsumer.accept(TaskFuture.fail(e));
                        } finally {
                            runId.set(null);
                        }
                    }));
                } catch (Throwable e) {
                    context.logger().error("提交任务[{}]失败.", getName(), e);
                    resultConsumer.accept(TaskFuture.fail(e));
                }
            }

            @Override
            public void stop() {
                runner.stop(runId.get());
            }
        };
    }

    protected interface TaskRunnable {
        void run(DataFlowNodeContext context, Consumer<TaskFuture<Object>> resultConsumer) throws Exception;
    }

}
