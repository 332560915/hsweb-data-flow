package org.hswebframework.data.flow.repository;

import org.hswebframework.data.flow.repository.entity.DataFlowProcessDefinitionEntity;

public interface DataFlowProcessDefinitionRepository {

    DataFlowProcessDefinitionEntity findById(String id);


}
