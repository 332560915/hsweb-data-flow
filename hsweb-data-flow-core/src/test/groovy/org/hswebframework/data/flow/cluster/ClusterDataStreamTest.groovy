package org.hswebframework.data.flow.cluster

import org.hswebframework.data.flow.cluster.local.LocalDataFlowClusterManager
import spock.lang.Specification

/**
 * TODO 完成注释
 * @author zhouhao
 * @since
 */
class ClusterDataStreamTest extends Specification {

    LocalDataFlowClusterManager clusterManager;

    def setup() {
        clusterManager = new LocalDataFlowClusterManager();

    }

    def "测试简单的流处理"() {

        given:
        def list = [];
        def stream = new ClusterDataStream(clusterManager, "test", { sink ->
            for (i in 1..111) {
                //Thread.sleep(100)
                //println i
                def fi = i;
                sink.write(fi)
            }
        });
        new ClusterDataStream(clusterManager, "test")
                .map({ i -> "str" + i })
                .buffer(10)
                .forEach({ str -> list.addAll(str); println str });

        stream.startSteam();
        stream.close()
        expect:
        list.size() == 111


    }
}
