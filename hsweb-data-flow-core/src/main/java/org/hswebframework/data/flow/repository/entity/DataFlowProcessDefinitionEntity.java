package org.hswebframework.data.flow.repository.entity;

import lombok.Getter;
import lombok.Setter;
import org.hswebframework.data.flow.model.PersistentDataFlowProcessDefinition;

@Getter
@Setter
public class DataFlowProcessDefinitionEntity extends PersistentDataFlowProcessDefinition {

    private static final long serialVersionUID = -6849794470754667710L;

    private String id;

    private String name;

    private String description;

}
