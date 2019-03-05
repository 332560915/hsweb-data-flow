package org.hswebframework.data.flow.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    //流式流程
    private boolean stream;

    private List<DataFlowTaskDefinition> tasks = new ArrayList<>();

    public List<DataFlowTaskDefinition> getFirstTask() {
        return tasks.stream()
                .filter(def -> CollectionUtils.isEmpty(def.getInputs()))
                .collect(Collectors.toList());
    }

    @Deprecated
    public Optional<DataFlowTaskDefinition> getStartTask() {
        return tasks.stream()
                .filter(node -> DataFlowTaskDefinition.TYPE_START.equals(node.getType()) || node.getInputs().isEmpty())
                .findFirst();
    }
}
