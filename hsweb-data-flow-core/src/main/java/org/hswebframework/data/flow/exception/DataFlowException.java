package org.hswebframework.data.flow.exception;

import lombok.Getter;

/**
 * @author zhouhao
 * @since 1.0.0
 */
public class DataFlowException extends RuntimeException {

    @Getter
    private ErrorCode errorCode;

    public DataFlowException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataFlowException(ErrorCode errorCode) {
        super(errorCode.getText(), null);
        this.errorCode = errorCode;
    }

    public DataFlowException(ErrorCode errorCode, String message) {
        super(message, null);
        this.errorCode = errorCode;
    }

    public DataFlowException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public DataFlowException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getText(), cause);
        this.errorCode = errorCode;
    }

    public DataFlowException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
