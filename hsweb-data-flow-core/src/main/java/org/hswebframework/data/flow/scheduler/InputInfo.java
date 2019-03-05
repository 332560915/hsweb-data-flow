package org.hswebframework.data.flow.scheduler;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author zhouhao
 * @since 1.0.0
 */
@Getter
@Setter
public class InputInfo implements Serializable {
    private static final long serialVersionUID = -6849794470754667710L;

    private String queue;
}
