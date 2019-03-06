package org.hswebframework.data.flow.cluster;

import org.hswebframework.data.flow.model.DataFlowTaskDefinition;

import java.util.List;
import java.util.Optional;

/**
 * @author zhouhao
 * @since 1.0.0
 */
public interface WorkerSelector {
     Optional<List<NodeInfo>> selectWorker(DataFlowTaskDefinition task);
}
