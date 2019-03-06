package org.hswebframework.data.flow.worker;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hswebframework.data.flow.api.*;
import org.hswebframework.data.flow.api.factory.DataFlowNodeRunnableFactory;
import org.hswebframework.data.flow.cluster.DataFlowClusterManager;
import org.hswebframework.data.flow.cluster.NodeInfo;
import org.hswebframework.data.flow.cluster.NodeRole;
import org.hswebframework.data.flow.scheduler.ClusterWorkerJobRequest;
import org.hswebframework.data.flow.scheduler.InputInfo;
import org.hswebframework.data.flow.scheduler.Queues;
import org.hswebframework.data.flow.standard.StandardDataFlowNodeContext;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author zhouhao
 * @since 1.0.0
 */
@Getter
@Setter
@Slf4j
public class DefaultDataFlowWorker implements DataFlowWorker {

    private NodeInfo worker;

    private String workerId;

    private String workerHost;

    private List<NodeRole> nodeRoles;

    private DataFlowClusterManager clusterManager;

    private TaskRunner taskRunner;

    private DataFlowNodeRunnableFactory runnableFactory;

    private ExpressionEvaluator expressionEvaluator;

    private TypeConverter typeConverter;

    private final Map<String, DataFlowNodeTaskRunnable> runnableMap = new ConcurrentHashMap<>();

    public void startJob(ClusterWorkerJobRequest request) {
        //收到了重复的启动通知
        synchronized (runnableMap) {
            DataFlowNodeTaskRunnable old = runnableMap.get(request.getInstanceId());
            if (null != old) {
                return;
            }
        }

        DataFlowNodeTaskRunnable runnable = runnableFactory.create(request.getExecutableDefinition());
        runnableMap.put(request.getInstanceId(), runnable);

        Consumer<Object> inputConsumer = input -> {
            StandardDataFlowNodeContext context = new StandardDataFlowNodeContext(request.getInstanceId());
            context.setPreNodeResult(input);
            context.setSuccess(true);
            context.setTypeConvertor(typeConverter);
            try {
                runnable.run(context, future -> {
                    Object result = future.success() ? future.get() : ErrorInfo.of(future.cause());
                    request.getOutputInfoList().stream()
                            .filter(outputInfo -> outputInfo.eval(expressionEvaluator, future.success(), result))
                            .forEach(outputInfo -> clusterManager.getQueue(outputInfo.getQueue()).add(result));
                });
            } catch (Throwable e) {
                context.logger().error("执行任务[{}]失败", request.getTaskId(), e);
                request.getOutputInfoList().stream()
                        .filter(outputInfo -> outputInfo.eval(expressionEvaluator, false, ErrorInfo.of(e)))
                        .forEach(outputInfo -> clusterManager.getQueue(outputInfo.getQueue()).add(ErrorInfo.of(e)));
            }
        };

        clusterManager.getQueue(request.getStopQueue()).accept(stop -> runnable.stop());

        for (InputInfo inputInfo : request.getInputQueue()) {
            clusterManager.getQueue(inputInfo.getQueue()).accept(inputConsumer);
        }
    }

    public void start() {
        worker = new NodeInfo();
        worker.setId(workerId);
        worker.setHost(workerHost);
        worker.setLastPingTime(System.currentTimeMillis());
        worker.setUpTime(System.currentTimeMillis());
        worker.setRoles(nodeRoles);
        worker.<ClusterWorkerJobRequest>getQueue(Queues.workerAcceptJob, clusterManager)
                .accept(request -> {
                    log.info("accept job request:{}", request);
                    startJob(request);
                });
        //注册
        clusterManager.getMap("data-flow-nodes").put(workerId, worker);
        //上线
        clusterManager.getTopic("data-flow-node-join").send(worker);
    }

    public void stop() {
        //发送下线通知
        clusterManager.getTopic("data-flow-node-leave").send(worker);
        //注销
        clusterManager.getMap("data-flow-nodes").remove(workerId);
    }

}
