package org.hswebframework.data.flow.standard.factory.supports;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.lang.annotation.Documented;
import java.util.List;

/**
 * @author zhouhao
 * @since 1.0.0
 */
@Getter
@Setter
@EqualsAndHashCode
public class JavaInvokeConfig {

    private String className;

    private String methodName;

    private List<Object> parameters;

    public Object getParameter(int index) {
        if (parameters == null || parameters.size() <= index) {
            return null;
        }
        return parameters.get(index);
    }
}
