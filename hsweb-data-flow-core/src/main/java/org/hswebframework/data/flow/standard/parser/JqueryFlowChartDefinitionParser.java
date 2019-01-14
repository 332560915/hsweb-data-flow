package org.hswebframework.data.flow.standard.parser;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.data.flow.model.DataFlowProcessDefinition;

import java.util.List;
import java.util.Map;

/**
 * @author zhouhao
 * @since 1.0.0
 */
public class JqueryFlowChartDefinitionParser implements DataFlowProcessDefinitionParserStrategy {
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
        private String                           title;
        private Map<String, Map<String, Object>> inputs;
        private Map<String, Map<String, Object>> outputs;
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

        DataFlowProcessDefinition definition = new DataFlowProcessDefinition();


        return null;
    }

    public static void main(String[] args) {
        String json = "{\n" +
                "  \"operators\": {\n" +
                "    \"operator1\": {\n" +
                "      \"top\": 20,\n" +
                "      \"left\": 20,\n" +
                "      \"properties\": {\n" +
                "        \"title\": \"Operator 1\",\n" +
                "        \"inputs\": {},\n" +
                "        \"outputs\": {\n" +
                "          \"output_1\": {\n" +
                "            \"label\": \"Output 1\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    \"operator2\": {\n" +
                "      \"top\": 80,\n" +
                "      \"left\": 300,\n" +
                "      \"properties\": {\n" +
                "        \"title\": \"Operator 2\",\n" +
                "        \"inputs\": {\n" +
                "          \"input_1\": {\n" +
                "            \"label\": \"Input 1\"\n" +
                "          },\n" +
                "          \"input_2\": {\n" +
                "            \"label\": \"Input 2\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"outputs\": {}\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"links\": {\n" +
                "    \"link_1\": {\n" +
                "      \"fromOperator\": \"operator1\",\n" +
                "      \"fromConnector\": \"output_1\",\n" +
                "      \"toOperator\": \"operator2\",\n" +
                "      \"toConnector\": \"input_2\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"operatorTypes\": {}\n" +
                "}";
        JQueryFlowChartData flowChartData = JSON.parseObject(json, JQueryFlowChartData.class);


    }
}
