package com.renj.imageloaderlibrary.loader;

import android.support.annotation.NonNull;

import com.renj.imageloaderlibrary.config.ImageLoadLibrary;
import com.renj.imageloaderlibrary.config.ImageModuleConfig;
import com.renj.imageloaderlibrary.utils.Utils;

import java.util.Map;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2018-03-09   15:11
 * <p>
 * 描述：LoaderModule 帮助类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
/*public*/ class ImageLoaderHelper {
    private ImageModuleConfig imageModuleConfig;

    /**
     * 初始化方法
     *
     * @param imageModuleConfig {@link ImageModuleConfig} 对象
     */
    ImageLoaderHelper(@NonNull ImageModuleConfig imageModuleConfig) {
        this.imageModuleConfig = imageModuleConfig;
        Utils.initUtils(imageModuleConfig.getApplication());

        Map<ImageLoadLibrary, IImageLoaderModule> loaderModuleMap = imageModuleConfig.getLoaderModuleMap();
        for (ImageLoadLibrary imageLoadLibrary : loaderModuleMap.keySet()) {
            loaderModuleMap.get(imageLoadLibrary).init(imageModuleConfig);
        }
    }

    /**
     * 获取配置信息
     *
     * @return {@link ImageModuleConfig}
     */
    public ImageModuleConfig getImageModuleConfig() {
        return imageModuleConfig;
    }

    /**
     * 获取默认加载框架
     *
     * @return 默认加载框架，{@link IImageLoaderModule} 子类对象
     */
    @org.jetbrains.annotations.Contract(pure = true)
    <T extends IImageLoaderModule> T getDefaultImageLoaderModule() {
        return (T) imageModuleConfig.getDefaultImageLoaderModule();
    }

    /**
     * 获取指定的加载框架
     *
     * @param imageLoadLibrary {@link ImageLoadLibrary}
     * @param <T>
     * @return
     */
    <T extends IImageLoaderModule> T getImageLoaderModule(@NonNull ImageLoadLibrary imageLoadLibrary) {
        return (T) imageModuleConfig.getLoaderModuleMap().get(imageLoadLibrary);
    }
}
