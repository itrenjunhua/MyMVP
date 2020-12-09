package com.renj.mvp.utils;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.renj.glide.GlideLoaderModule;
import com.renj.imageloaderlibrary.config.ImageLoadConfig;
import com.renj.imageloaderlibrary.config.ImageLoadLibrary;
import com.renj.imageloaderlibrary.config.ImageModuleConfig;
import com.renj.imageloaderlibrary.loader.IImageLoaderModule;
import com.renj.imageloaderlibrary.loader.ImageLoaderModule;
import com.renj.mvp.R;

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
        ImageLoaderModule.initImageLoaderModule(
                new ImageModuleConfig.Builder(application)
                        .defaultImageLoadModule(ImageLoadLibrary.GLIDE_LIBRARY, new GlideLoaderModule())
                        .build());
    }

    /**
     * 获取图片加载Module {@link com.renj.imageloaderlibrary.loader.IImageLoaderModule} 的子类对象。
     *
     * @return 返回 {@link com.renj.imageloaderlibrary.loader.IImageLoaderModule} 子类对象
     */
    public static IImageLoaderModule getDefaultImageLoaderModule() {
        return ImageLoaderModule.getDefaultImageLoaderModule();
    }

    public static void loadImage(Context context, String url, ImageView imageView) {
        ImageLoadConfig config = new ImageLoadConfig
                .Builder()
                .context(context)
                .url(url)
                .target(imageView)
                .loadingImageId(R.mipmap.default_loading)
                .errorImageId(R.mipmap.default_loading)
                .build();
        getDefaultImageLoaderModule().loadImage(config);
    }
}
