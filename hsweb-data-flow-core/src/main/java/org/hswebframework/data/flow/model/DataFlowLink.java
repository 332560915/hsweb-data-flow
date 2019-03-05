package org.hswebframework.data.flow.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhouhao
 * @since 1.0.0
 */
@Getter
@Setter
public class DataFlowLink {

    private String id;

    private String name;

    private String type;

    private LinkCondition condition;

    private List<DataFlowTaskDefinition> sourceNodes = new ArrayList<>();

    private List<DataFlowTaskDefinition> targetNodes = new ArrayList<>();

    public boolean matchIfSourceSuccess() {
        return "success".equals(type);
    }

    public boolean matchIfSourceError() {
        return "error".equals(type);
    }

    public boolean matchCondition(Map<String, Object> context) {
        return condition == null || condition.match(context);
    }

    public DataFlowLink addSourceNode(DataFlowTaskDefinition sourceNode) {
        if (this.sourceNodes.stream().anyMatch(node -> node.getId().equals(sourceNode.getId()))) {
            return this;
        }
        this.sourceNodes.add(sourceNode);
        return this;
    }

    public DataFlowLink addTargetNode(DataFlowTaskDefinition targetNode) {

        if (this.targetNodes.stream().anyMatch(node -> node.getId().equals(targetNode.getId()))) {
            return this;
        }
        this.targetNodes.add(targetNode);
        return this;
    }
}
