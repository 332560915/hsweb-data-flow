package org.hswebframework.data.flow.api.repository.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataFlowTaskEntity {
    private String id;

    private String processId;

    private String processName;

    private String parametersJson;

    private String currentNodeId;

    private String currentNodeRunnerId;

    private boolean status;

    private float progress;

    private Long startTime;

    private Long endTime;

}
