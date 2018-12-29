package org.hswebframework.data.flow.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

    private String des;

    private List<DataFlowNode> nodes;
}
