package org.hswebframework.data.flow.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 数据流处理节点
 *
 * @author zhouhao
 * @since 1.0.0
 */
@Getter
@Setter
@SuppressWarnings("all")
public class DataFlowTaskDefinition {

    public static final String TYPE_START = "start";
    public static final String TYPE_END   = "end";

    private String id;

    private String name;

    private String description;

    private String type;

    private String taskType;

    private long executeTimeout = 0;

    private TimeUnit timeoutUnit = TimeUnit.SECONDS;

    //节点配置
    private Map<String, Object> config;

    //输入节点
    private List<DataFlowLink> inputs = new ArrayList<>();

    //输出节点
    private List<DataFlowLink> outputs = new ArrayList<>();

    public DataFlowLink getInputLink(String id, Supplier<DataFlowLink> linkSupplier) {
        return inputs.stream()
                .filter(link -> id.equals(link.getId()))
                .findFirst()
                .orElseGet(() -> {
                    DataFlowLink newLink = linkSupplier.get();
                    newLink.setId(id);
                    inputs.add(newLink);
                    return newLink;
                });
    }

    public DataFlowLink getOuputLink(String id, Supplier<DataFlowLink> linkSupplier) {
        return outputs.stream()
                .filter(link -> id.equals(link.getId()))
                .findFirst()
                .orElseGet(() -> {
                    DataFlowLink newLink = linkSupplier.get();
                    newLink.setId(id);
                    outputs.add(newLink);
                    return newLink;
                });
    }
}
