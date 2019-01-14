package org.hswebframework.data.flow.repository.entity;

import lombok.Getter;
import lombok.Setter;
import org.hswebframework.data.flow.model.DataFlowTaskDefinition;

import java.io.Serializable;

@Getter
@Setter
public class DataFlowNodeTaskEntity implements Serializable {

    private static final long serialVersionUID = -6849794470754667710L;

    /**
     * @see DataFlowProcessDefinitionEntity#getId()
     */
    private String processId;

    /**
     * @see DataFlowTaskEntity#getId()
     */
    private String taskId;

    /**
     * @see DataFlowTaskDefinition#getId()
     */
    private String nodeId;

    /**
     * @see DataFlowTaskDefinition#getName()
     */
    private String nodeName;

    private String preNodeId;

    private String preNodeName;

    private String preTaskId;

    private boolean running;

    private float progress;

    private long startTime;

    private long endTime;

    private boolean success;

    private String workerId;

    private String workerHost;

}
