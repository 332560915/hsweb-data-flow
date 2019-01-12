package org.hswebframework.data.flow.standard;

import lombok.Getter;
import lombok.Setter;
import org.hswebframework.data.flow.api.DataFlowContext;
import org.hswebframework.data.flow.api.DataFlowNodeContext;
import org.hswebframework.data.flow.api.DataFlowNodeTask;
import org.hswebframework.data.flow.api.DataFlowTask;
import org.hswebframework.data.flow.api.factory.DataFlowNodeRunnableFactory;
import org.hswebframework.data.flow.model.DataFlowModel;
import org.hswebframework.data.flow.model.DataFlowNode;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class StandardDataFlowTask implements DataFlowTask {
    private String id;

    private String processId;

    private String modelName;

    private String modelId;

    private long createTime;

    private long startTime;

    private DataFlowNodeRunnableFactory runnableFactory;

    private DataFlowModel dataFlowModel;

    private volatile boolean running;

    public DataFlowContext newContext(Map<String, Object> parameter) {
        return null;
    }

    public DataFlowNodeContext newNodeContext(DataFlowContext context, Object preResult) {
        return null;
    }

    protected DataFlowNodeTask createDataFlowNodeRunner(DataFlowNode node) {

        return null;
    }

    protected List<DataFlowNode> getNextNode(DataFlowNode currentNode, DataFlowNodeContext preNodeContext, DataFlowContext context) {


        return null;
    }

    protected void end(DataFlowContext context) {

    }

    private void doStartNode(DataFlowNode node, DataFlowContext context, Object preResult) {
        context.logger().info("开始执行节点:{}", node.getName());

        DataFlowNodeContext nodeContext = newNodeContext(context, preResult);
        DataFlowNodeTask task = createDataFlowNodeRunner(node);
        task.start(nodeContext, future -> {
            Object result = future.get();
            if (DataFlowNode.TYPE_END.equals(node.getType())) {
                end(context);
                return;
            }
            List<DataFlowNode> nextNodes = getNextNode(node, nodeContext, context);
            //没有下一步节点,结束流程
            if (nextNodes == null || nextNodes.isEmpty()) {
                end(context);
                return;
            }
            //执行下一步
            for (DataFlowNode nextNode : nextNodes) {
                doStartNode(nextNode, context, result);
            }
        });
    }

    @Override
    public void start(DataFlowContext context) {
        doStartNode(dataFlowModel
                        .getStartNode()
                        .orElseThrow(UnsupportedOperationException::new)
                , context
                , null);
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
