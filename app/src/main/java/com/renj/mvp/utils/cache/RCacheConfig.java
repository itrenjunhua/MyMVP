package com.renj.mvp.utils.cache;

import java.io.File;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-12-15   17:48
 * <p>
 * 描述：缓存工具类中的配制文件
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class RCacheConfig {
    public static final long SECOND = 1000;
    public static final long MINUTE = SECOND * 60;
    public static final long HOUR = MINUTE * 60;
    public static final long DAY = HOUR * 24;
    /**
     * 指定缓存大小 默认大小12M
     */
    public static final long CACHE_SIZE = 1024 * 1024 * 12;

    /**
     * 时间和具体内容之间的分隔符，尽量避免具体内容中可能出现的值
     */
    public static String SPLITVALUE = "&-=SPLIT_char_VALUE=-&";
    /**
     * 过期标记
     */
    public static String OUT_TIME_FLAG = "&-=OUT_TIME_FLAG=-&";
    /**
     * 缓存文件扩展名
     */
    public static String EXTEND_NAME = ".cache";
    /**
     * 缓存路径
     */
    public static File CACHE_PATH;
    /**
     * 缓存大小检查和删除文件线程
     */
    public static RCacheSizeControl RCACHE_SIZE_CONTROL;
}
