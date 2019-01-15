package org.hswebframework.data.flow.standard.factory

import org.hswebframework.data.flow.api.SyncTaskRunner
import org.hswebframework.data.flow.api.factory.DataFlowNodeRunnableFactory
import org.hswebframework.data.flow.model.DataFlowTaskDefinition
import org.hswebframework.data.flow.standard.StandardDataFlowNodeContext
import org.hswebframework.data.flow.standard.factory.supports.JavaMethodInvokeStrategy
import spock.lang.Specification

/**
 * @author zhouhao
 * @since 1.0.0
 */
class StandardDataFlowNodeRunnableFactoryTest extends Specification {

    DataFlowNodeRunnableFactory factory;

    def setup() {
        factory = new StandardDataFlowNodeRunnableFactory();
        factory.addStrategy(new JavaMethodInvokeStrategy(new SyncTaskRunner("test-runner")))
    }

    def "测试调用java方法"() {
        def definition = new DataFlowTaskDefinition(
                id: "test",
                taskType: "java-method",
                name: "test",
                config: [
                        "className" : "org.hswebframework.data.flow.standard.factory.TestClass",
                        "methodName": "staticMethod"
                ]
        )
        given:
        def runnable = factory.create(definition);
        when:
        runnable != null
        then:
        def result = null
        runnable.run(new StandardDataFlowNodeContext("test"), { future ->
            result = future.get()
        })
        expect:
        result == "staticMethod"
    }
}
