package org.hswebframework.data.flow.api.repository.entity;

import lombok.Getter;
import lombok.Setter;
import org.hswebframework.data.flow.model.DataFlowNode;

@Getter
@Setter
public class DataFlowNodeTaskEntity {

    /**
     * @see DataFlowProcessEntity#getId()
     */
    private String processId;

    /**
     * @see DataFlowTaskEntity#getId()
     */
    private String taskId;

    /**
     * @see DataFlowNode#getId()
     */
    private String nodeId;

    /**
     * @see DataFlowNode#getName()
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
