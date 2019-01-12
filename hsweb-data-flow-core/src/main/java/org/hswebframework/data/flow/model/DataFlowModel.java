package org.hswebframework.data.flow.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 数据流处理模型
 *
 * @author zhouhao
 * @since 1.0.0
 */
@Getter
@Setter
public class DataFlowModel {
    private String id;

    private String name;

    private String description;

    private long version;

    private List<DataFlowNode> nodes = new ArrayList<>();

    public Optional<DataFlowNode> getStartNode() {

        return nodes.stream()
                .filter(node -> DataFlowNode.TYPE_START.equals(node.getType()))
                .findFirst();
    }
}
