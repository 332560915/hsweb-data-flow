package org.hswebframework.data.flow.standard

import org.hswebframework.data.flow.api.AsyncTaskRunner
import org.hswebframework.data.flow.api.SyncTaskRunner
import org.hswebframework.data.flow.model.PersistentDataFlowProcessDefinition
import org.hswebframework.data.flow.standard.factory.StandardDataFlowNodeRunnableFactory
import org.hswebframework.data.flow.standard.factory.supports.JavaMethodInvokeStrategy
import org.hswebframework.data.flow.standard.parser.JQueryFlowChartDefinitionParser
import org.hswebframework.data.flow.standard.parser.StandardDataFlowProcessDefinitionParser
import spock.lang.Specification

import java.util.concurrent.Executors

/**
 * @author zhouhao
 * @since 1.0.0
 */
class StandardDataFlowServiceTest extends Specification {

    def parser = new StandardDataFlowProcessDefinitionParser()

    def service = new StandardDataFlowService()

    def setup() {
        def runner = new AsyncTaskRunner(Executors.newFixedThreadPool(2))

        parser.getStrategies().add(new JQueryFlowChartDefinitionParser())
        def factory = new StandardDataFlowNodeRunnableFactory()
        factory.addStrategy(new JavaMethodInvokeStrategy(runner))

        service.setDefinitionParser(parser)
        service.setRunnableFactory(factory)
    }

    def "测试简单流程"() {
        def json = """
                        {
                          "operators": {
                            "operator1": {
                              "top": 20,
                              "left": 20,
                              "properties": {
                                "title": "开始",
                                "type":"start",
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
                                "outputs": {
                                 "output_2": {
                                    "label": "进入步骤1"
                                  }
                                }
                              }
                            },
                            "operator3": {
                              "top": 80,
                              "left": 300,
                              "properties": {
                                "title": "输出内容2",
                                "taskType":"java-method",
                                "config":{
                                    "className":"org.hswebframework.data.flow.standard.factory.TestClass",
                                    "methodName":"method"
                                },
                                "inputs": {
                                  "input_3": {
                                    "label": "Input 3"
                                  },
                                  "input_4": {
                                    "label": "Input 4"
                                  }
                                },
                                "outputs": {}
                              }
                            },
                          },
                          "links": {
                            "link_1": {
                              "fromOperator": "operator1",
                              "fromConnector": "output_1",
                              "toOperator": "operator2",
                              "toConnector": "input_2"
                            },
                             "link_2": {
                              "fromOperator": "operator2",
                              "fromConnector": "output_2",
                              "toOperator": "operator3",
                              "toConnector": "input_3"
                            }
                          },
                          "operatorTypes": {}
                        }
        """
        def persistent = new PersistentDataFlowProcessDefinition();
        persistent.setName("test")
        persistent.setDefinitionId("test")
        persistent.setMetaFormat("jf")
        persistent.setMeta(json)
        persistent.setVersion(1L)
        given:
        def definition = parser.parse(persistent)
        service.startDataFlow(definition, [:])
        expect:
        definition != null
        Thread.sleep(3000)
    }
}
