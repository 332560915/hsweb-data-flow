package org.hswebframework.data.flow.repository.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author zhouhao
 * @since 1.0.0
 */
@Getter
@Setter
public class DataFlowNodeDataEntity implements Serializable {

    private static final long serialVersionUID = -6849794470754667710L;

    private String id;

    private String nodeId;

    private String nodeName;

    private String taskId;

    private String preTaskId;

    private String processId;

    private String processName;

    //数据类型,如:static,stream
    private String dataType;

    //格式化配置,如: {"type":"list":"javaType":"org.xxx.aaa.XxxBean"}
    private String formatConfigJson;

    //数据内容json格式
    private String dataJson;

}
