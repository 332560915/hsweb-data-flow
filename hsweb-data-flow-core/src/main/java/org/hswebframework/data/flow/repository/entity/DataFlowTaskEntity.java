package org.hswebframework.data.flow.repository.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DataFlowTaskEntity implements Serializable {

    private static final long serialVersionUID = -6849794470754667710L;

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
