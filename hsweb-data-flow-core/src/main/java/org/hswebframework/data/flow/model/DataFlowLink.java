package org.hswebframework.data.flow.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

    private List<DataFlowNode> sourceNode;

    private List<DataFlowNode> targetNode;

}
