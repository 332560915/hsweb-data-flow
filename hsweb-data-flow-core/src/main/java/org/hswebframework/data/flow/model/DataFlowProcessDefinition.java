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
public class DataFlowProcessDefinition {
    private String id;

    private String name;

    private long version;

    private List<DataFlowTaskDefinition> nodes = new ArrayList<>();

    public Optional<DataFlowTaskDefinition> getStartNode() {
        return nodes.stream()
                .filter(node -> DataFlowTaskDefinition.TYPE_START.equals(node.getType()))
                .findFirst();
    }
}
