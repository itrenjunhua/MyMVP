package com.renj.mvp.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.module.AppGlideModule;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2018-03-06   23:37
 * <p>
 * 描述：自定义的 GlideModule，改变 GlideModule 的默认配置信息
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class CustomerGlideModule extends AppGlideModule {

    // 磁盘缓存大小 50M
    public static final int DISK_CACHE_SIZE = 50 * 1024 * 1024;
    // 缓存目录
    public static final String DISK_CACHE_NAME = "glide_cache";

    /**
     * setMemoryCache()
     * 用于配置Glide的内存缓存策略，默认配置是LruResourceCache。
     * <p>
     * setBitmapPool()
     * 用于配置Glide的Bitmap缓存池，默认配置是LruBitmapPool。
     * <p>
     * setDiskCache()
     * 用于配置Glide的硬盘缓存策略，默认配置是InternalCacheDiskCacheFactory。
     * <p>
     * setDiskCacheService()
     * 用于配置Glide读取缓存中图片的异步执行器，默认配置是FifoPriorityThreadPoolExecutor，
     * 也就是先入先出原则。
     * <p>
     * setResizeService()
     * 用于配置Glide读取非缓存中图片的异步执行器，默认配置也是FifoPriorityThreadPoolExecutor。
     * <p>
     * setDecodeFormat()
     * 用于配置Glide加载图片的解码模式，默认配置是RGB_565。
     */
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder glideBuilder) {
        /**
         * 更改缓存最总文件夹名称
         *
         * 是在sdcard/Android/data/包名/cache/DISK_CACHE_NAME目录当中
         */
        glideBuilder.setDiskCache(new ExternalCacheDiskCacheFactory(context, DISK_CACHE_NAME, DISK_CACHE_SIZE));

        /**
         * Glide也能使用ARGB_8888的图片格式
         * 虽然图片质量变好了，但同时内存开销也会明显增大
         */
        glideBuilder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
    }
}
