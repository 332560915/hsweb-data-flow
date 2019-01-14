package org.hswebframework.data.flow.api;

/**
 * @author zhouhao
 * @since 1.0.0
 */
public interface TaskRunner {

    String getRunnerId();

    String run(Runnable runnable);

    void stop(String id);
}
