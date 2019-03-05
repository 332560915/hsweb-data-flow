package org.hswebframework.data.flow.scheduler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hswebframework.data.flow.cluster.DataFlowClusterManager;
import org.hswebframework.data.flow.cluster.Queue;

/**
 * @author zhouhao
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum Queues {
    workerAcceptJob("worker:job:accept", ClusterWorkerJobRequest.class),

    workerStopJob("worker:job:stop", Object.class);

    private String queueId;

    private Class type;

    public <T> Queue<T> getQueue(DataFlowClusterManager clusterManager) {
        return clusterManager.getQueue(this.queueId);
    }

}
