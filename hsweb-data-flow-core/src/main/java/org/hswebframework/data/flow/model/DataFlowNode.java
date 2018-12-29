package org.hswebframework.data.flow.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 数据流处理节点
 *
 * @author zhouhao
 * @since 1.0.0
 */
@Getter
@Setter
public class DataFlowNode {
    private String id;

    private String name;

    private String description;

    private String type;

    //节点配置
    private DataFlowNodeConfig config;

    //输入节点
    private List<DataFlowLink> inputs;

    //输出节点
    private List<DataFlowLink> outputs;
}
