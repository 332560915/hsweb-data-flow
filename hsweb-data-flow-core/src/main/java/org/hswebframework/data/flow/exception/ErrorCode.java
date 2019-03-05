package org.hswebframework.data.flow.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhouhao
 * @since 1.0.0
 */
@AllArgsConstructor
@Getter
public enum ErrorCode {

    CannotFoundStartNode("启动节点不存在"),
    WorkerNotAlive("没有可用的工作节点");

    private String text;

    public DataFlowException createException() {
        return new DataFlowException(this);
    }

    public DataFlowException createException(String message) {
        return new DataFlowException(this, message);
    }

    public DataFlowException createException(Throwable throwable) {
        return new DataFlowException(this, throwable);
    }
}
