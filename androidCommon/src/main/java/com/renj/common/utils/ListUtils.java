package com.renj.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2018-12-28   15:52
 * <p>
 * 描述：操作 {@link List} 的工具类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ListUtils {

    /**
     * 复制 {@link List}
     *
     * @param source
     * @param <T>
     * @return
     */
    public static <T> List<T> copy(List<T> source) {
        List<T> target = new ArrayList<>();
        if (null == source) return target;
        for (T t : source) {
            target.add(t);
        }
        return target;
    }

    /**
     * 将数组变为 {@link List} 集合
     *
     * @param array
     * @param <T>
     * @return
     */
    public static <T> List<T> fromArray(T[] array) {
        List<T> tList = new ArrayList<>();
        for (T applyType : array) {
            tList.add(applyType);
        }
        return tList;
    }

    /**
     * 取出2个集合找那个不同的元素
     *
     * @param src
     * @param target
     * @param <T>
     * @return
     */
    public static <T> List<T> rem(List<T> src, List<T> target) {
        List<T> result = new ArrayList<>();
        for (T t : src) {
            if (!target.contains(t)) result.add(t);
        }
        return result;
    }

    /**
     * 取出2个集合中相同的元素
     *
     * @param src
     * @param target
     * @param <T>
     * @return
     */
    public static <T> List<T> contains(List<T> src, List<T> target) {
        List<T> result = new ArrayList<>();
        for (T t : target) {
            if (src.contains(t)) result.add(t);
        }
        return result;
    }

    /**
     * 判断 {@link List} 是否为 {@code null} 或者大小为 0
     * @param list
     * @param <T>
     * @return
     */
    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }
}
