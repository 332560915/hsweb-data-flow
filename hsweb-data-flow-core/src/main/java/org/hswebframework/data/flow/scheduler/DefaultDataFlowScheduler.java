package org.hswebframework.data.flow.scheduler;

import org.hswebframework.data.flow.cluster.DataFlowClusterManager;
import org.hswebframework.data.flow.cluster.NodeInfo;
import org.hswebframework.data.flow.cluster.WorkerSelector;
import org.hswebframework.data.flow.exception.ErrorCode;
import org.hswebframework.data.flow.model.DataFlowLink;
import org.hswebframework.data.flow.model.DataFlowProcessDefinition;
import org.hswebframework.data.flow.model.DataFlowTaskDefinition;
import org.hswebframework.data.flow.model.LinkCondition;
import org.hswebframework.data.flow.utils.IdUtils;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author zhouhao
 * @since 1.0.0
 */
public class DefaultDataFlowScheduler implements DataFlowScheduler {

    private String schedulerId;

    private WorkerSelector workerSelector;

    private DataFlowClusterManager dataFlowClusterManager;

    private Map<String, SchedulerRunner> runners = new ConcurrentHashMap<>();

    private class SchedulerRunner {
        private String id;

        private DataFlowProcessDefinition definition;

        void setUp(DataFlowTaskDefinition task) {
            NodeInfo workerNode = workerSelector.selectWorker(task).orElseThrow(ErrorCode.WorkerNotAlive::createException);
            ClusterWorkerJobRequest jobRequest = new ClusterWorkerJobRequest();
            jobRequest.setInstanceId(id);
            jobRequest.setTaskId(task.getId());
            jobRequest.setSchedulerId(schedulerId);
            jobRequest.setContextId("data-flow-context:" + id);
            jobRequest.setStopQueue("data-flow-stop:" + id);
            jobRequest.setExecutableDefinition(task.getExecutableDefinition());

            //设置数据输入的队列
            jobRequest.setInputQueue(task.getInputs().stream()
                    .map(input -> {
                        InputInfo inputInfo = new InputInfo();
                        inputInfo.setQueue("data-flow:" + id + ":" + input.getId());
                        return inputInfo;
                    }).collect(Collectors.toList()));

            //设置数据输出的队列
            jobRequest.setOutputInfoList(task.getOutputs().stream()
                    .map(output -> {
                        OutputInfo outputInfo = new OutputInfo();
                        LinkCondition condition = output.getCondition();
                        if (condition != null) {
                            outputInfo.setCondition(condition.getExpression());
                            outputInfo.setConditionType(condition.getType());
                        }
                        outputInfo.setQueue("data-flow:" + id + ":" + output.getId());
                        return outputInfo;
                    }).collect(Collectors.toList()));

            //向节点发送任务
            workerNode.getQueue(Queues.workerAcceptJob, dataFlowClusterManager).add(jobRequest);
        }

        void start() {
            definition.getTasks().forEach(this::setUp);
        }

        void stop() {
            dataFlowClusterManager
                    .getQueue("data-flow-stop:" + id)
                    .add("stop-with-scheduler");
        }

    }

    @Override
    public String start(DataFlowProcessDefinition definition) {
        String id = IdUtils.newUUID();
        SchedulerRunner runner = new SchedulerRunner();
        runner.id = id;
        runner.definition = definition;
        runners.put(id, runner);
        runner.start();
        return id;
    }

    @Override
    public void stop(String id) {
        Optional.ofNullable(runners.get(id))
                .ifPresent(SchedulerRunner::stop);
    }
}
