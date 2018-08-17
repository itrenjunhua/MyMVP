package com.renj.imageloaderlibrary.glide;

import com.renj.imageloaderlibrary.loader.IImageLoaderModule;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2018-04-27   17:27
 * <p>
 * 描述：Glide加载框架扩展方法
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public interface IGlideLoaderModule extends IImageLoaderModule {
    /**
     * 暂停加载
     */
    void pause();

    /**
     * 开始加载
     */
    void resume();

    /**
     * 清除内存缓存
     */
    void clearMemoryCache();

    /**
     * 根据级别清除内存中的缓存
     *
     * @param level 需要清除的级别
     */
    void trimMemory(int level);

    /**
     * 清除所有内存缓存
     */
    void clearAllMemoryCaches();

    /**
     * 清除磁盘缓存
     */
    void clearDiskCache();
}
