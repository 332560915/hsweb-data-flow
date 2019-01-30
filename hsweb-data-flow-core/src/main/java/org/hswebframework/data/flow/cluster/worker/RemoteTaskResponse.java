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
public class RemoteTaskResponse implements Serializable {

    private static final long serialVersionUID = -1L;

    private String id;

    private Object response;

    private String responseType;
}
