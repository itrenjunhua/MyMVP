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
 * 创建时间：2018-03-07   17:44
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ImageLoader {
    private static ImageLoader imageLoader = new ImageLoader();
    private static IImageLoaderFactory iImageLoaderFactory;

    private ImageLoader() {
    }

    public static void initImageLoaderFactory(@NonNull IImageLoaderFactory iImageLoaderFactory) {
        ImageLoader.iImageLoaderFactory = iImageLoaderFactory;
    }

    @org.jetbrains.annotations.Contract(pure = true)
    public static ImageLoader newInstance() {
        if (iImageLoaderFactory == null)
            throw new RuntimeException("没有调用 ImageLoader。initImageLoaderFactory(IImageLoaderFactory) 方法进行初始化");
        return imageLoader;
    }

    /**
     * 从网络中加载图片
     *
     * @param view 显示图片的控件
     * @param path 图片路径
     */
    public void loadImage(@NonNull View view, @NonNull String path) {
        iImageLoaderFactory.loadImage(view, path);
    }

    /**
     * 从资源文件中加载图片
     *
     * @param view       显示图片的控件
     * @param drawableId 图片id
     */
    public void loadImage(@NonNull View view, @DrawableRes int drawableId) {
        iImageLoaderFactory.loadImage(view, drawableId);
    }

    /**
     * 从文件中加载图片
     *
     * @param view 显示图片的控件
     * @param file 图片文件
     */
    public void loadImage(@NonNull View view, @NonNull File file) {
        iImageLoaderFactory.loadImage(view, file);
    }

    /**
     * 清除内存缓存
     */
    public void clearMemoryCache() {
        iImageLoaderFactory.clearMemoryCache();
    }

    /**
     * 清除磁盘缓存
     */
    public void clearDiskCache() {
        iImageLoaderFactory.clearDiskCache();
    }
}
