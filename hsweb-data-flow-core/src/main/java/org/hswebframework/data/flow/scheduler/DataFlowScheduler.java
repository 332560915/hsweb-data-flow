package org.hswebframework.data.flow.scheduler;

import org.hswebframework.data.flow.model.DataFlowProcessDefinition;

/**
 * 数据流程调度器,用于分发,调度,管理任务
 *
 * @author zhouhao
 * @since 1.0.0
 */
public interface DataFlowScheduler {

    /**
     * 启动任务
     *
     * @param definition 任务定义ID
     * @return 任务实例ID
     */
    String start(DataFlowProcessDefinition definition);

    /**
     * 停止任务
     *
     * @param id 任务实体
     * @return
     */
    void stop(String id);

}
