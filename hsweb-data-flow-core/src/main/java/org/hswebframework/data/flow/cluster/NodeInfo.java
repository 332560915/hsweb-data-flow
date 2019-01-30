package org.hswebframework.data.flow.cluster;

import lombok.Data;

import java.util.List;

/**
 * @author zhouhao
 * @since 1.0.0
 */
@Data
public class NodeInfo {
    private String id;

    private String host;

    private List<NodeRole> roles;

    private long lastPingTime = System.currentTimeMillis();

    private long upTime = System.currentTimeMillis();

    public boolean isWorker() {
        return roles != null && roles.contains(NodeRole.WORKER);
    }

    public boolean isScheduler() {
        return roles != null && roles.contains(NodeRole.SCHEDULER);
    }
}
