package org.hswebframework.data.flow.model;

import lombok.Getter;
import lombok.Setter;
import org.hswebframework.data.flow.api.ExpressionEvaluator;

import java.util.Map;

/**
 * 链接条件
 *
 * @author zhouhao
 * @since 1.0.0
 */
@Getter
@Setter
public class LinkCondition {
    //spel or javascript
    private String type;

    // data.format=='json'
    private String expression;

    private ExpressionEvaluator evaluator;

    public boolean match(Map<String, Object> context) {
        if (null != evaluator) {
            Object result = evaluator.evaluate(type, expression, context);
            return Boolean.TRUE.equals(result) || "true".equals(result);
        }
        return true;
    }
}
