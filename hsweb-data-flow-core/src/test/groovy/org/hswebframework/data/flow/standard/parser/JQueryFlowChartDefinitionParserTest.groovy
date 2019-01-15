package org.hswebframework.data.flow.standard.parser

import spock.lang.Specification

class JQueryFlowChartDefinitionParserTest extends Specification {
    def parser = new JQueryFlowChartDefinitionParser();


    def "test"() {
        def json = """
                        {
                          "operators": {
                            "operator1": {
                              "top": 20,
                              "left": 20,
                              "properties": {
                                "title": "开始",
                                "taskType":"start",
                                "inputs": {},
                                "outputs": {
                                  "output_1": {
                                    "label": "进入步骤1"
                                  }
                                }
                              }
                            },
                            "operator2": {
                              "top": 80,
                              "left": 300,
                              "properties": {
                                "title": "输出内容",
                                "taskType":"java-method",
                                "config":{
                                    "className":"org.hswebframework.data.flow.standard.factory.TestClass",
                                    "methodName":"staticMethod"
                                },
                                "inputs": {
                                  "input_1": {
                                    "label": "Input 1"
                                  },
                                  "input_2": {
                                    "label": "Input 2"
                                  }
                                },
                                "outputs": {}
                              }
                            }
                          },
                          "links": {
                            "link_1": {
                              "fromOperator": "operator1",
                              "fromConnector": "output_1",
                              "toOperator": "operator2",
                              "toConnector": "input_2"
                            }
                          },
                          "operatorTypes": {}
                        }
        """

        given:
        def define = parser.parse(json);
        expect:
        define != null
        define.getTasks() != null
        define.getTasks().size()==2
        define.getStartTask().isPresent()
        define.getStartTask().get().id=='operator1'
        define.getStartTask().get().getOutputs().get(0).getTargetNodes().get(0).getId()=='operator2'
        define.getStartTask().get().getOutputs().get(0).getTargetNodes().get(0).getTaskType()=='java-method'
        define.getStartTask().get().getOutputs().get(0).getTargetNodes().get(0).getConfig().get("className")!=null
        define.getStartTask().get().getOutputs().get(0).getTargetNodes().get(0).getConfig().get("methodName")!=null



    }

}
