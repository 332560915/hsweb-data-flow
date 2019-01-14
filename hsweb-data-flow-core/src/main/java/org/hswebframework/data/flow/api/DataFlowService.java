package org.hswebframework.data.flow.api;

import java.util.Map;

/**
 * @author zhouhao
 * @since 1.0.0
 */
public interface DataFlowService {

    String startDataFlow(String processId, Map<String, Object> parameter);

}
