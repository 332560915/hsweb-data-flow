package org.hswebframework.data.flow.model;

import lombok.Getter;
import lombok.Setter;

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

    private List<DataFlowTaskDefinition> sourceNode;

    private List<DataFlowTaskDefinition> targetNode;

    public boolean matchCondition(Map<String, Object> context) {
        return condition == null || condition.match( context);
    }
}
