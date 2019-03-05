package org.hswebframework.data.flow.cluster;

import java.util.List;
import java.util.Set;

/**
 * @author zhouhao
 * @since 1.0.0
 */
public interface ClusterMap<K, V> {
    V get(K key);

    V put(K key, V value);

    V remove(K key);

    List<V> values();

    Set<K> keySet();
}
