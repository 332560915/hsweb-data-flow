package org.hswebframework.data.flow.standard.parser;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.data.flow.model.DataFlowLink;
import org.hswebframework.data.flow.model.DataFlowProcessDefinition;
import org.hswebframework.data.flow.model.DataFlowTaskDefinition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author zhouhao
 * @since 1.0.0
 */
public class JQueryFlowChartDefinitionParser implements DataFlowProcessDefinitionParserStrategy {
    @Override
    public boolean support(String format) {
        return "jf".equalsIgnoreCase(format);
    }

    @Getter
    @Setter
    public static class JQueryFlowChartData {
        private Map<String, Operator> operators;

        private Map<String, Link> links;
    }

    @Getter
    @Setter
    public static class Operator {
        OperatorProperties properties;
    }

    @Getter
    @Setter
    public static class OperatorProperties {
        private String title;
        private String taskType;
        private String type;

        private Map<String, Object> config;

        private Map<String, Map<String, Object>> inputs  = new HashMap<>();
        private Map<String, Map<String, Object>> outputs = new HashMap<>();

        public boolean inputIsEmpty() {
            return inputs == null || inputs.isEmpty();
        }

        public boolean outputIsEmpty() {
            return outputs == null || outputs.isEmpty();
        }
    }

    @Getter
    @Setter
    public static class Link {
        private String fromOperator;
        private String fromConnector;
        private String fromSubConnector;
        private String toOperator;
        private String toConnector;
        private String toSubConnector;
    }

    @Override
    public DataFlowProcessDefinition parse(String meta) {
        JQueryFlowChartData flowChartData = JSON.parseObject(meta, JQueryFlowChartData.class);

        Map<String, DataFlowTaskDefinition> taskCache = new HashMap<>();

        Function<String, DataFlowTaskDefinition> newDefinition = id -> {
            Operator operator = flowChartData.getOperators().get(id);
            DataFlowTaskDefinition definition = new DataFlowTaskDefinition();
            definition.setId(id);
            definition.setName(operator.properties.title);
            definition.setTaskType(operator.properties.taskType);
            definition.setType(operator.properties.type);
            definition.setConfig(operator.properties.config);

            return definition;
        };

        for (Map.Entry<String, Link> linkEntry : flowChartData.getLinks().entrySet()) {
            String linkId = linkEntry.getKey();
            Link link = linkEntry.getValue();
            Operator operator = flowChartData.getOperators().get(link.fromOperator);

            DataFlowTaskDefinition from = taskCache.computeIfAbsent(link.fromOperator, newDefinition);
            DataFlowTaskDefinition to = taskCache.computeIfAbsent(link.toOperator, newDefinition);


            from.getOuputLink(linkId, () -> {
                DataFlowLink flowLink = new DataFlowLink();
                Map<String, Object> linkProperties = operator.getProperties().outputs.get(link.fromOperator);
                // TODO: 2019/1/14 更多属性

                return flowLink;
            }).addSourceNode(from)
                    .addTargetNode(to);

            to.getInputLink(linkId, () -> {
                DataFlowLink flowLink = new DataFlowLink();
                Map<String, Object> linkProperties = operator.getProperties().outputs.get(link.fromOperator);
                // TODO: 2019/1/14 更多属性
                return flowLink;
            }).addSourceNode(from)
                    .addTargetNode(to);

        }
        DataFlowProcessDefinition definition = new DataFlowProcessDefinition();

        List<DataFlowTaskDefinition> taskDefinitions = new ArrayList<>(taskCache.values());
        definition.setTasks(taskDefinitions);

        return definition;
    }

}
