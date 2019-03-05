package org.hswebframework.data.flow.standard;

import lombok.Getter;
import lombok.Setter;
import org.hswebframework.data.flow.api.*;
import org.hswebframework.data.flow.api.factory.DataFlowNodeRunnableFactory;
import org.hswebframework.data.flow.model.DataFlowLink;
import org.hswebframework.data.flow.model.DataFlowProcessDefinition;
import org.hswebframework.data.flow.model.DataFlowTaskDefinition;
import org.hswebframework.data.flow.utils.IdUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StandardDataFlowProcess implements DataFlowProcess {
    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private long createTime;

    @Getter
    @Setter
    private long startTime;

    @Getter
    @Setter
    private DataFlowNodeRunnableFactory runnableFactory;

    @Getter
    @Setter
    private DataFlowProcessDefinition definition;

    private volatile boolean running = false;

    public DataFlowContext newContext(Map<String, Object> parameter) {
        return new StandardDataFlowContext(definition.getName(), parameter);
    }

    public DataFlowNodeContext newNodeContext(DataFlowContext context,
                                              DataFlowTaskDefinition node,
                                              DataFlowTaskDefinition preNode,
                                              Object preResult) {
        StandardDataFlowNodeContext nodeContext = new StandardDataFlowNodeContext(node.getName());
        nodeContext.setFlowContext(context);
        nodeContext.setPreNodeResult(preResult);
        if (preNode != null) {
            nodeContext.setPreNodeId(preNode.getId());
            nodeContext.setPreNodeType(preNode.getType());
            nodeContext.setPreNodeName(preNode.getName());
        }
        return nodeContext;
    }

    protected DataFlowNodeTask createDataFlowNodeTask(DataFlowTaskDefinition node) {
        DataFlowNodeTaskRunnable runnable = runnableFactory.create(node.getExecutableDefinition());
        StandardDataFlowNodeTask task = new StandardDataFlowNodeTask();
        task.setId(IdUtils.newUUID());
        task.setNodeId(node.getId());
        task.setNodeName(node.getName());
        task.setRunnable(runnable);
        return task;
    }

    protected List<DataFlowTaskDefinition> getNextNode(DataFlowTaskDefinition currentNode,
                                                       DataFlowNodeContext nodeContext,
                                                       DataFlowContext context) {
        return currentNode.getOutputs()
                .stream()
                .filter(dataFlowLink -> {
                    Map<String, Object> expressionContext = new HashMap<>();
                    expressionContext.put("flow", context);
                    expressionContext.put("node", nodeContext);
                    //如果是成功才执行的节点
                    if (dataFlowLink.matchIfSourceSuccess()) {
                        return nodeContext.isSuccess() && dataFlowLink.matchCondition(expressionContext);
                    }
                    //如果是失败才执行的节点
                    if (dataFlowLink.matchIfSourceError()) {
                        return !nodeContext.isSuccess() && dataFlowLink.matchCondition(expressionContext);
                    }
                    return dataFlowLink.matchCondition(expressionContext);
                })
                .map(DataFlowLink::getTargetNodes)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    protected void end(DataFlowContext context) {
        context.progress().set(100F);
        context.logger().info("结束");
    }

    private void doStartNode(DataFlowTaskDefinition node, DataFlowContext context, DataFlowTaskDefinition preNode, Object preResult) {
        context.logger().info("开始执行:{}", node.getName());
        DataFlowNodeContext nodeContext = newNodeContext(context, node, preNode, preResult);
        DataFlowNodeTask task = createDataFlowNodeTask(node);
        task.start(nodeContext, future -> {
            Object result = null;
            if (!future.success()) {
                nodeContext.error(future.cause());
            } else {
                result = future.get();
                nodeContext.success(result);
            }
            List<DataFlowTaskDefinition> nextNodes = getNextNode(node, nodeContext, context);
            //没有下一步节点,结束流程
            if (nextNodes == null || nextNodes.isEmpty()) {
                if (!context.isStream()) {
                    end(context);
                }
                return;
            }
            //执行下一步
            for (DataFlowTaskDefinition nextNode : nextNodes) {
                doStartNode(nextNode, context, node, result);
            }
        });
    }

    @Override
    public void start(DataFlowContext context) {
        doStartNode(definition
                        .getStartTask()
                        .orElseThrow(UnsupportedOperationException::new)
                , context
                , null
                , null);
        running = true;
    }

    @Override
    public void stop() {
        running = false;
    }

    @Override
    public void pause() {
        running = false;
    }
}
