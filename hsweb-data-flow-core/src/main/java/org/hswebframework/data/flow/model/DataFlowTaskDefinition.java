package org.hswebframework.data.flow.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 数据流处理节点
 *
 * @author zhouhao
 * @since 1.0.0
 */
@Getter
@Setter
public class DataFlowTaskDefinition {

    public static final String TYPE_START = "start";
    public static final String TYPE_END = "end";

    private String id;

    private String name;

    private String description;

    private String type;

    private long executeTimeout = 0;

    private TimeUnit timeoutUnit = TimeUnit.SECONDS;

    //节点配置
    private Map<String, Object> config;

    //输入节点
    private List<DataFlowLink> inputs;

    //输出节点
    private List<DataFlowLink> outputs;

}
