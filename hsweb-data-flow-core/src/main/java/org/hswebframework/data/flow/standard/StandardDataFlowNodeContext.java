package org.hswebframework.data.flow.standard;

import lombok.Getter;
import lombok.Setter;
import org.hswebframework.data.flow.api.DataFlowContext;
import org.hswebframework.data.flow.api.DataFlowNodeContext;
import org.hswebframework.data.flow.api.Logger;
import org.hswebframework.data.flow.api.Progress;

import java.util.Optional;

/**
 * @author zhouhao
 * @since 1.0.0
 */
public class StandardDataFlowNodeContext implements DataFlowNodeContext {
    @Getter
    @Setter
    private DataFlowContext flowContext;

    @Getter
    @Setter
    private String preNodeType;

    @Getter
    @Setter
    private String preNodeName;

    @Getter
    @Setter
    private String preNodeId;

    @Getter
    @Setter
    private Object preNodeResult;

    private Logger logger;

    private Progress progress;

    public StandardDataFlowNodeContext(String name) {
        this.logger = new Slf4jLogger("data.flow.task." + name);
        this.progress = new Slf4jProgress("data.flow.task.progress", name);
    }

    @Override
    public <T> Optional<T> getPreNodeResult(Class<T> type) {
        if (type.isInstance(preNodeResult)) {
            return Optional.ofNullable((T) preNodeResult);
        }
        return Optional.empty();
    }

    @Override
    public Logger logger() {
        return logger;
    }

    @Override
    public Progress progress() {
        return progress;
    }
}
