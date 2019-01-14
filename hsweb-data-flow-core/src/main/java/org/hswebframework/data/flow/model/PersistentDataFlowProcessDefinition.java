package org.hswebframework.data.flow.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 持久化的流程定义
 *
 * @author zhouhao
 * @since 1.0.0
 */
@Getter
@Setter
public class PersistentDataFlowProcessDefinition implements Serializable {
    private String definitionId;

    private String name;

    private long version;

    private String metaFormat;

    private String meta;
}
