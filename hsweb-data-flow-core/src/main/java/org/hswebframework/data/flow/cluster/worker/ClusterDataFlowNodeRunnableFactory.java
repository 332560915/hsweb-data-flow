package org.hswebframework.data.flow.cluster.worker;

import lombok.extern.slf4j.Slf4j;
import org.hswebframework.data.flow.api.DataFlowNodeContext;
import org.hswebframework.data.flow.api.DataFlowNodeTaskRunnable;
import org.hswebframework.data.flow.api.TaskFuture;
import org.hswebframework.data.flow.api.factory.DataFlowNodeRunnableFactory;
import org.hswebframework.data.flow.cluster.DataFlowClusterManager;
import org.hswebframework.data.flow.cluster.NodeInfo;
import org.hswebframework.data.flow.cluster.Queue;
import org.hswebframework.data.flow.cluster.WorkerSelector;
import org.hswebframework.data.flow.exception.ErrorCode;
import org.hswebframework.data.flow.model.DataFlowTaskDefinition;
import org.hswebframework.data.flow.utils.IdUtils;

import java.util.function.Consumer;

/**
 * @author zhouhao
 * @since 1.0.0
 */
@Slf4j
public class ClusterDataFlowNodeRunnableFactory implements DataFlowNodeRunnableFactory {

    private DataFlowClusterManager clusterManager;

    private WorkerSelector workerSelector;

    private Queue<RemoteTaskRequest> remoteTaskRequestQueue;

    @Override
    public DataFlowNodeTaskRunnable create(DataFlowTaskDefinition node) {
        NodeInfo worker = workerSelector.selectWorker().orElseThrow(ErrorCode.WorkerNotAlive::createException);
        log.debug("使用[{}]执行节点[{}]", worker, node);
        RemoteTaskRequest executing = new RemoteTaskRequest();
        executing.setId(IdUtils.newUUID());
        executing.setConfig(node.getConfig());
        executing.setTaskType(node.getTaskType());

        String runnerId = worker.getId();
        return new DataFlowNodeTaskRunnable() {
            @Override
            public String getRunnerId() {
                return runnerId;
            }

            @Override
            public void run(DataFlowNodeContext context, Consumer<TaskFuture<Object>> resultConsumer) throws Exception {
                String queueId = "task-response:" + executing.getId();
                Queue<RemoteTaskResponse> queue = clusterManager.getQueue(queueId);

                queue.accept(response -> resultConsumer.accept(createFuture(response)));

                remoteTaskRequestQueue.add(executing);
            }

            @Override
            public void stop() {
                clusterManager.getQueue("task-stop").add(executing.getId());
            }
        };
    }

    public void start(){

    }

    private TaskFuture<Object> createFuture(RemoteTaskResponse response) {
        return TaskFuture.success(response.getResponse());
    }


}
