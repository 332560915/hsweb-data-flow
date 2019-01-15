package org.hswebframework.data.flow.cluster;

import lombok.Getter;
import org.hswebframework.data.flow.api.DataStream;
import org.hswebframework.data.flow.api.Sink;
import org.hswebframework.data.flow.utils.IdUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author zhouhao
 * @since 1.0.0
 */
public class ClusterDataStream<T> implements DataStream<T> {

    private DataFlowClusterManager clusterManager;

    private Queue<T> queue;

    @Getter
    private String streamId;

    private volatile boolean running;

    private Consumer<Sink<T>> sinkConsumer;

    private Runnable onClose = () -> {
    };

    public ClusterDataStream(DataFlowClusterManager clusterManager,
                             String streamId) {
        this.clusterManager = clusterManager;
        this.queue = clusterManager.getQueue("data.flow.stream." + streamId);
        this.streamId = streamId;
        this.sinkConsumer = sink -> queue.accept(sink::write);
    }

    public ClusterDataStream(DataFlowClusterManager clusterManager,
                             String streamId,
                             Consumer<Sink<T>> sinkConsumer) {
        this.clusterManager = clusterManager;
        this.queue = clusterManager.getQueue("data.flow.stream." + streamId);
        this.streamId = streamId;
        this.sinkConsumer = sinkConsumer;
    }

    @Override
    public DataStream<T> filter(Predicate<T> predicate) {
        return new ClusterDataStream<>(clusterManager,
                IdUtils.newUUID(),
                sink -> {
                    onClose(sink::close);
                    queue.accept(data -> {
                        if (predicate.test(data)) {
                            sink.write(data);
                        }
                        if (!running) {
                            sink.close();
                        } else if (sink.isClose()) {
                            close();
                        }
                    });
                    startSteam();
                });
    }

    @Override
    public <R> DataStream<R> map(Function<T, R> mapping) {
        return new ClusterDataStream<>(clusterManager,
                IdUtils.newUUID(),
                sink -> {
                    onClose(sink::close);
                    queue.accept(data -> {
                        sink.write(mapping.apply(data));
                        if (!running) {
                            sink.close();
                        } else if (sink.isClose()) {
                            close();
                        }
                    });
                    startSteam();
                });
    }

    @Override
    public DataStream<List<T>> buffer(int bufferSize) {
        return new ClusterDataStream<>(clusterManager,
                IdUtils.newUUID(),
                (sink) -> {
                    AtomicInteger counter = new AtomicInteger(0);
                    List<T> buffer = new ArrayList<>(bufferSize);
                    onClose(() -> {
                        synchronized (buffer) {
                            if (!buffer.isEmpty()) {
                                sink.write(new ArrayList<>(buffer));
                                counter.set(0);
                                buffer.clear();
                            }
                        }
                    });
                    queue.accept(data -> {
                        synchronized (buffer) {
                            buffer.add(data);
                        }
                        if (!running) {
                            sink.close();
                            return;
                        } else if (sink.isClose()) {
                            close();
                            return;
                        }
                        int now = counter.incrementAndGet();
                        if (now % bufferSize == 0) {
                            synchronized (buffer) {
                                sink.write(new ArrayList<>(buffer));
                                counter.set(0);
                                buffer.clear();
                            }
                        }
                    });
                    startSteam();
                });
    }

    public void startSteam() {
        this.running = true;
        queue.onClose(() -> {
            if (running) {
                running = false;
                onClose.run();
            }
        });

        if (running && sinkConsumer != null) {
            sinkConsumer.accept(new Sink<T>() {
                @Override
                public void write(T data) {
                    if (isClose()) {
                        return;
                    }
                    queue.add(data);
                }

                @Override
                public void close() {
                    ClusterDataStream.this.close();
                }

                @Override
                public boolean isClose() {
                    return !running;
                }
            });
        }
    }

    @Override
    public void forEach(Consumer<T> consumer) {
        queue.accept(consumer);
        startSteam();
    }

    protected void onClose(Runnable runnable) {
        Runnable old = this.onClose;
        this.onClose = () -> {
            old.run();
            runnable.run();
        };
    }

    @Override
    public void close() {
        if (running) {
            running = false;
            onClose.run();
            queue.close();
        }
    }
}
