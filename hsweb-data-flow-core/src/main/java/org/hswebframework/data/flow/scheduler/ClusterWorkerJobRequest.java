package org.hswebframework.data.flow.scheduler;

import lombok.Getter;
import lombok.Setter;
import org.hswebframework.data.flow.model.ExecutableDefinition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhouhao
 * @since 1.0.0
 */
@Getter
@Setter
public class ClusterWorkerJobRequest implements Serializable {
    private static final long serialVersionUID = -6849794470754667710L;

    private String instanceId;

    private String taskId;

    private String schedulerId;

    private String stopQueue;

    private ExecutableDefinition executableDefinition;

    /**
     * @see org.hswebframework.data.flow.cluster.DataFlowClusterManager#getMap(String)
     */
    private String contextId;

    //输入
    private List<InputInfo> inputQueue = new ArrayList<>();

    //输出
    private List<OutputInfo> outputInfoList = new ArrayList<>();

}
