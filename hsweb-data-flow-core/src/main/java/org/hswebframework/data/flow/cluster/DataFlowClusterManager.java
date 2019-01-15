package org.hswebframework.data.flow.cluster;

import java.util.concurrent.TimeUnit;

/**
 * @author zhouhao
 * @since 1.0.0
 */
public interface DataFlowClusterManager {

    <T> Queue<T> getQueue(String name);

    <T> Topic<T> getTopic(String name);

    Lock getLock(String lockName, long timeout, TimeUnit timeUnit);

    ClusterMap getMap(String map);

}
