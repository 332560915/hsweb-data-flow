package org.hswebframework.data.flow.api.repository;

import org.hswebframework.data.flow.api.repository.entity.DataFlowProcessEntity;

public interface DataFlowProcessRepository {

    DataFlowProcessEntity findByProcessId(String processId);


}
