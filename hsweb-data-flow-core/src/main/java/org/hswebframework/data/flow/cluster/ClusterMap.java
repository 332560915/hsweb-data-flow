package org.hswebframework.data.flow.cluster;

/**
 * @author zhouhao
 * @since 1.0.0
 */
public interface ClusterMap<K, V> {
    V get(K key);

    V put(K key, V value);
}
