package org.hswebframework.data.flow.api;

import lombok.extern.slf4j.Slf4j;
import org.hswebframework.data.flow.utils.IdUtils;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author zhouhao
 * @since 1.0.0
 */
@Slf4j
public class AsyncTaskRunner implements TaskRunner {
    private String id;

    private ExecutorService executorService;

    private Map<String, Future<?>> futureMap = new ConcurrentHashMap<>();

    public AsyncTaskRunner(ExecutorService executorService) {
        this(IdUtils.newUUID(), executorService);
    }

    public AsyncTaskRunner(String id, ExecutorService executorService) {
        this.id = id;
        this.executorService = executorService;
    }

    @Override
    public String getRunnerId() {
        return id;
    }

    @Override
    public String run(Runnable runnable) {
        String id = IdUtils.newUUID();
        futureMap.put(id, executorService.submit(() -> {
            try {
                runnable.run();
            } finally {
                futureMap.remove(id);
            }
        }));
        return id;
    }

    @Override
    public void stop(String id) {
        try {
            Optional.ofNullable(futureMap.get(id))
                    .filter(future -> !future.isCancelled())
                    .ifPresent(future -> future.cancel(true));
            futureMap.remove(id);
        } catch (Exception e) {
            log.warn("stop job[{}] error", id, e);
        }
    }
}
