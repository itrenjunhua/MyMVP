package com.renj.mvp.imageloader;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.View;

import java.io.File;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2018-03-07   17:33
 * <p>
 * 描述：图片加载对外接口，用于与具体的图片加载框架进行隔离，便于替换框架
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public interface IImageLoaderFactory {
    /**
     * 从网络中加载图片
     *
     * @param view 显示图片的控件
     * @param path 图片路径
     */
    void loadImage(@NonNull View view, @NonNull String path);

    /**
     * 从资源文件中加载图片
     *
     * @param view       显示图片的控件
     * @param drawableId 图片id
     */
    void loadImage(@NonNull View view, @DrawableRes int drawableId);

    /**
     * 从文件中加载图片
     *
     * @param view 显示图片的控件
     * @param file 图片文件
     */
    void loadImage(@NonNull View view, @NonNull File file);

    /**
     * 清除内存缓存
     */
    void clearMemoryCache();

    /**
     * 清除磁盘缓存
     */
    void clearDiskCache();
}
