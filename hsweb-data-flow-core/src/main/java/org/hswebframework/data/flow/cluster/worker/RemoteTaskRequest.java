package org.hswebframework.data.flow.cluster.worker;

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
public class RemoteTaskRequest implements Serializable {

    private static final long serialVersionUID = -1L;

    private String id;

    private String taskType;

    private Map<String, Object> config;
}
