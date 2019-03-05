package org.hswebframework.data.flow.worker;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhouhao
 * @since 1.0.0
 */
@Getter
@Setter
public class ErrorInfo {
    private String type;

    private String message;

    public static ErrorInfo of(Throwable e) {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.message = e == null ? "未知错误" : e.getMessage();
        errorInfo.type = e == null ? "unknown" : e.getClass().getSimpleName();
        return errorInfo;
    }
}
