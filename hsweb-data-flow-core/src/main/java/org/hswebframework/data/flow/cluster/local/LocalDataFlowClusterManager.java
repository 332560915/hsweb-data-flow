package org.hswebframework.data.flow.cluster.local;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hswebframework.data.flow.cluster.*;
import org.hswebframework.data.flow.utils.IdUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * @author zhouhao
 * @since 1.0.0
 */
@Slf4j
@SuppressWarnings("all")
public class LocalDataFlowClusterManager implements DataFlowClusterManager {

    private ExecutorService executorService;

    private Map<String, Semaphore> semaphoreMap = new ConcurrentHashMap<>();

    private Map<String, Queue> queueMap = new ConcurrentHashMap<>();

    @Override
    public <T> Queue<T> getQueue(String name) {
        return queueMap.computeIfAbsent(name, n -> {
                    return new Queue<T>() {
                        Consumer<T> consumer;

                        private List<T> cache = new ArrayList<>();

                        protected Runnable onClose;

                        private boolean closed = false;

                        @Override
                        public T take() {
                            // TODO: 19-1-15
                            return null;
                        }

                        private void flush() {
                            if (!cache.isEmpty()) {
                                synchronized (cache) {
                                    cache.forEach(consumer);
                                    cache.clear();
                                }
                            }
                        }

                        @Override
                        public void accept(Consumer<T> consumer) {
                            if (this.consumer == null) {
                                this.consumer = consumer;
                            } else {
                                this.consumer.andThen(consumer);
                            }
                            flush();
                        }

                        @Override
                        public void add(T data) {
                            if (consumer != null) {
                                consumer.accept(data);
                                flush();
                            } else {
                                cache.add(data);
                            }
                        }

                        @Override
                        public synchronized void onClose(Runnable runnable) {
                            if (onClose == null) {
                                onClose = runnable;
                            } else {
                                Runnable old = onClose;
                                onClose = () -> {
                                    old.run();
                                    runnable.run();
                                };
                            }
                        }

                        @Override
                        public synchronized void close() {
                            queueMap.remove(name);
                            if (null != onClose) {
                                onClose.run();
                                onClose = null;
                            }
                        }
                    };
                }
        );
    }

    @Override
    public <T> Topic<T> getTopic(String name) {
        return null;
    }

    @Override
    @SneakyThrows
    public Lock getLock(String lockName, long timeout, TimeUnit timeUnit) {
        String id = IdUtils.newUUID();
        log.debug("try lock [{}],id:{}", lockName, id);
        Semaphore semaphore = semaphoreMap.computeIfAbsent(lockName, name -> new Semaphore(1));

        boolean success = semaphore.tryAcquire(timeout, timeUnit);

        if (!success) {
            throw new TimeoutException("lock [" + lockName + "] timeout " + timeout + timeUnit.name());
        }
        return () -> {
            semaphore.release();
            log.debug("unlock [{}],id:{}", lockName, id);
        };
    }

    @Override
    public <K,V> ClusterMap<K,V>  getMap(String map) {

        return null;
    }

    @Override
    public List<NodeInfo> getAllNode() {
        return null;
    }
}
