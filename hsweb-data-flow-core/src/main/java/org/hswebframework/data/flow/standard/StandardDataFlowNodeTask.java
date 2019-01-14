package org.hswebframework.data.flow.standard;

import lombok.Getter;
import lombok.Setter;
import org.hswebframework.data.flow.api.DataFlowNodeContext;
import org.hswebframework.data.flow.api.DataFlowNodeTask;
import org.hswebframework.data.flow.api.DataFlowNodeTaskRunnable;
import org.hswebframework.data.flow.api.TaskFuture;

import java.util.function.Consumer;

/**
 * @author zhouhao
 * @since 1.0.0
 */
@Getter
@Setter
public class StandardDataFlowNodeTask implements DataFlowNodeTask {
    private String                   id;
    private String                   nodeId;
    private String                   nodeName;
    private DataFlowNodeTaskRunnable runnable;

    @Override
    public void start(DataFlowNodeContext context, Consumer<TaskFuture<Object>> resultHandle) {
        try {
            runnable.run(context, resultHandle);
        } catch (Throwable e) {
            resultHandle.accept(TaskFuture.fail(e));
        }
    }

    @Override
    public String getRunnerId() {
        return runnable.getRunnerId();
    }

    @Override
    public void stop() {
        runnable.stop();
    }
}
