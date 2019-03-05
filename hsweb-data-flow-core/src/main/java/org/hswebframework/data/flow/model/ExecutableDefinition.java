package org.hswebframework.data.flow.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

/**
 * @author zhouhao
 * @since 1.0.0
 */
@Getter
@Setter
public class ExecutableDefinition implements Serializable {
    private String type;

    private String name;

    private Map<String,Object> config;
}
