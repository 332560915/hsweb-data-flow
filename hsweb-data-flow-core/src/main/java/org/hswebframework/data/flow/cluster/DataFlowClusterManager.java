package org.hswebframework.data.flow.cluster;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author zhouhao
 * @since 1.0.0
 */
public interface DataFlowClusterManager {

    <T> Queue<T> getQueue(String name);

    <T> Topic<T> getTopic(String name);

    Lock getLock(String lockName, long timeout, TimeUnit timeUnit);

    ClusterMap getMap(String map);

    List<NodeInfo> getAllNode();

    default List<NodeInfo> getWokerNode() {
        return getAllNode()
                .stream()
                .filter(NodeInfo::isWorker)
                .collect(Collectors.toList());
    }
}
