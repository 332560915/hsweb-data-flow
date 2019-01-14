package org.hswebframework.data.flow.standard.factory;

import org.hswebframework.data.flow.api.DataFlowNodeTaskRunnable;

/**
 * @author zhouhao
 * @since 1.0.0
 */
public interface DataFlowNodeRunnableFactoryStrategy<C> {

    C newConfig();

    boolean support(String type);

    DataFlowNodeTaskRunnable create(C config);
}
