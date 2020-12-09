package com.renj.utils.collection;

import java.util.HashMap;
import java.util.Map;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2020-06-12   20:34
 * <p>
 * 描述：连续给Map集合添加数据的工具类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class MapUtils<K, V> {
    private Map<K, V> map;

    public MapUtils() {
        map = createMap();
    }

    /**
     * 创建HashMap<String, String>对象
     *
     * @return
     */
    private Map<K, V> createMap() {
        return new HashMap<K, V>();
    }

    /**
     * 将一个 key-value 值添加到map集合，方便链式调用
     *
     * @param key
     * @param value
     * @return
     */
    public MapUtils<K, V> put(K key, V value) {
        if (map == null) map = createMap();
        map.put(key, value);
        return this;
    }

    public Map<K, V> getMap() {
        return map;
    }
}
