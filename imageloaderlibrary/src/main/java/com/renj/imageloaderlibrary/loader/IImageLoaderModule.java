package com.renj.imageloaderlibrary.loader;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2018-03-08   10:30
 * <p>
 * 描述：图片加载接口顶级类，定义最基本的图片加载方法，可扩展。由子类实现。子类使用具体的框架实现
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public interface IImageLoaderModule {
    /**
     * 对框架进行初始化，参数必须为 {@link Application} ，强制在 Application 类中初始化
     *
     * @param application {@link Application}
     */
    void init(@NonNull Application application);

    /**
     * 简单的加载网络图片
     *
     * @param context   上下文
     * @param url       图片路径
     * @param imageView {@link ImageView} 控件
     */
    void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView);

    /**
     * 根据配置信息加载图片
     *
     * @param imageInfoConfig {@code T extends ImageInfoConfig} 对象
     * @param <T>             {@code T extends ImageInfoConfig}
     */
    <T extends ImageInfoConfig> void loadImage(@NonNull T imageInfoConfig);
}
