package com.renj.mvp.utils;

import android.app.Application;
import android.support.annotation.NonNull;

import com.renj.imageloaderlibrary.glide.GlideLoaderModule;
import com.renj.imageloaderlibrary.loader.IImageLoaderModule;
import com.renj.imageloaderlibrary.loader.ImageLoaderModule;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2018-08-17   14:03
 * <p>
 * 描述：图片加载管理类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ImageLoaderUtils {
    /**
     * 初始化自定义图片加载框架
     *
     * @param application {@link Application} 对象
     */
    public static void init(@NonNull Application application) {
        ImageLoaderModule.initImageLoaderModule(application, new GlideLoaderModule());
    }

    /**
     * 获取图片加载Module {@link com.renj.imageloaderlibrary.loader.IImageLoaderModule} 的子类对象。
     * <b>注意：这里返回的结果为{@link com.renj.imageloaderlibrary.loader.IImageLoaderModule} 子类对象，具体是哪一个根据在{@link #init(Application)} 方法中
     * 调用 {@link ImageLoaderModule#initImageLoaderModule(Application, IImageLoaderModule)} 方法传递的第二个参数确定。</b>
     *
     * @return 返回 {@link com.renj.imageloaderlibrary.loader.IImageLoaderModule} 子类对象
     */
    public static GlideLoaderModule getImageLoader() {
        return ImageLoaderModule.getImageLoaderModule();
    }
}
