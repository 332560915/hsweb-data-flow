package org.hswebframework.data.flow.api;

import lombok.Getter;
import lombok.Setter;
import org.hswebframework.data.flow.utils.IdUtils;

/**
 * @author zhouhao
 * @since 1.0.0
 */
@Getter
@Setter
public class SyncTaskRunner implements TaskRunner {
    private String id;

    public SyncTaskRunner() {
        this(IdUtils.newUUID());
    }

    public SyncTaskRunner(String id) {
        this.id = id;
    }

    @Override
    public String getRunnerId() {
        return id;
    }

    @Override
    public String run(Runnable runnable) {
        String id = IdUtils.newUUID();

        runnable.run();

        return id;
    }

    @Override
    public void stop(String id) {
        //do noting
    }
}
