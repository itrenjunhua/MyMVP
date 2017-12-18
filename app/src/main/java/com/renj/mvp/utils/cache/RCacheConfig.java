package com.renj.mvp.utils.cache;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
interface RCacheConfig {
    /**
     * 指定缓存大小 默认大小12M
     */
    long CACHE_SIZE = 1024 * 1024 * 12;
    /**
     * 时间和具体内容之间的分隔符，尽量避免具体内容中可能出现的值
     */
    String SPLIT_CHAR = "&-=SpLiTcHaR=-&";
    /**
     * 文件内容过期标记
     */
    String OUT_TIME_FLAG = "&-=OUT_TIME_FLAG=-&";
    /**
     * 缓存文件扩展名
     */
    String EXTEND_NAME = ".cache";
    /**
     * 当需要在子线程中工作时使用的线程池
     */
    ExecutorService EXECUTORSERVICE = Executors.newFixedThreadPool(2);
    /**
     * 主线程的Handler
     */
    Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());
}
