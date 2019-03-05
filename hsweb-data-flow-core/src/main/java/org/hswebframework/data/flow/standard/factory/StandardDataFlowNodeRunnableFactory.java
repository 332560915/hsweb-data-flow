package org.hswebframework.data.flow.standard.factory;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;
import org.hswebframework.data.flow.api.DataFlowNodeTaskRunnable;
import org.hswebframework.data.flow.api.factory.DataFlowNodeRunnableFactory;
import org.hswebframework.data.flow.model.DataFlowTaskDefinition;
import org.hswebframework.data.flow.model.ExecutableDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhouhao
 * @since 1.0.0
 */
@SuppressWarnings("all")
public class StandardDataFlowNodeRunnableFactory implements DataFlowNodeRunnableFactory {

    @Getter
    @Setter
    private List<DataFlowNodeRunnableFactoryStrategy> strategies = new ArrayList<>();

    public StandardDataFlowNodeRunnableFactory() {
        strategies.add(new DataFlowNodeRunnableFactoryStrategy() {
            @Override
            public Object newConfig() {
                return new Object();
            }

            @Override
            public boolean support(String type) {
                return type == null || type.isEmpty();
            }

            @Override
            public DataFlowNodeTaskRunnable create(Object config) {
                return DataFlowNodeTaskRunnable.doNoting();
            }
        });
    }

    @Override
    public DataFlowNodeTaskRunnable create(ExecutableDefinition node) {
        return strategies
                .stream()
                .filter(strategy -> strategy.support(node.getType()))
                .findFirst()
                .map(strategy -> strategy.create(copyConfig(strategy.newConfig(), node.getConfig())))
                .orElseThrow(() -> new UnsupportedOperationException("[" + node.getName() + "]:不支持的节点类型:" + node.getType()));
    }

    @SneakyThrows
    protected Object copyConfig(Object target, Map<String, Object> config) {
        if (null != config) {
            BeanUtils.copyProperties(target, config);
        }
        return target;
    }

    public void addStrategy(DataFlowNodeRunnableFactoryStrategy strategy) {
        strategies.add(strategy);
    }

}
