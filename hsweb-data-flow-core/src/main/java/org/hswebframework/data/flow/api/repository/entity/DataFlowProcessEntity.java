package org.hswebframework.data.flow.api.repository.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataFlowProcessEntity {

    private String id;

    private String name;

    private String modelId;

    private String modelName;

    private long modelVersion;

    private String modelMetaFormat;

    private String modelMeta;

    private String description;

}
