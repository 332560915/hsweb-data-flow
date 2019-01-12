package org.hswebframework.data.flow.model;

import lombok.Getter;
import lombok.Setter;

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

}
