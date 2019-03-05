package org.hswebframework.data.flow.scheduler;

import lombok.Getter;
import lombok.Setter;
import org.hswebframework.data.flow.api.ExpressionEvaluator;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhouhao
 * @since 1.0.0
 */
@Getter
@Setter
public class OutputInfo implements Serializable {
    private static final long serialVersionUID = -6849794470754667710L;

    //输出队列
    private String queue;

    //输出条件
    private String condition;

    //条件类型。如：js,groovy,event:error,event:success
    private String conditionType;

    public boolean eval(ExpressionEvaluator evaluator, boolean success, Object result) {
        if (condition == null || condition.isEmpty()) {
            return true;
        }
        if ("event".equals(conditionType)) {
            return success ? "success".equals(condition) : "error".equals(condition);
        }
        if (!success) {
            return false;
        }
        Map<String, Object> context = new HashMap<>();
        context.put("result", result);

        Object object = evaluator.evaluate(conditionType, condition, context);
        return Boolean.TRUE.equals(object) || "true".equals(object);
    }

}
