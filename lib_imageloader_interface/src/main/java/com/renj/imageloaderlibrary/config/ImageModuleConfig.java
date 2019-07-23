package com.renj.imageloaderlibrary.config;

import android.app.Application;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import com.renj.imageloaderlibrary.loader.IImageLoaderModule;
import com.renj.imageloaderlibrary.utils.CheckUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-01-22   10:43
 * <p>
 * 描述：自定义图片框架基本信息信息配置
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ImageModuleConfig {
    private Application application;
    @DrawableRes
    private int loadingRes;
    @DrawableRes
    private int errorRes;
    // 默认加载框架为第一个添加的框架
    private IImageLoaderModule defaultImageLoaderModule;
    // 支持的加载框架
    private Map<ImageLoadLibrary, IImageLoaderModule> loaderModuleMap;

    private ImageModuleConfig(Builder builder) {
        this.application = builder.application;
        this.loadingRes = builder.loadingRes;
        this.errorRes = builder.errorRes;
        this.defaultImageLoaderModule = builder.defaultImageLoaderModule;
        this.loaderModuleMap = builder.loaderModuleMap;
    }

    public Application getApplication() {
        return application;
    }

    public int getLoadingRes() {
        return loadingRes;
    }

    public int getErrorRes() {
        return errorRes;
    }

    public IImageLoaderModule getDefaultImageLoaderModule() {
        return defaultImageLoaderModule;
    }

    public Map<ImageLoadLibrary, IImageLoaderModule> getLoaderModuleMap() {
        return loaderModuleMap;
    }

    public static class Builder {
        private Application application;
        @DrawableRes
        private int loadingRes;
        @DrawableRes
        private int errorRes;
        // 默认加载框架为第一个添加的框架
        private IImageLoaderModule defaultImageLoaderModule;
        // 支持的加载框架
        private Map<ImageLoadLibrary, IImageLoaderModule> loaderModuleMap = new HashMap<>();

        public Builder(@NonNull Application application) {
            CheckUtils.checkNotNull(application, "Application 参数不能为 null.");

            this.application = application;
        }

        public Builder defaultImageLoadModule(@NonNull ImageLoadLibrary imageLoadLibrary, @NonNull IImageLoaderModule iImageLoaderModule) {
            addImageLoadModule(imageLoadLibrary, iImageLoaderModule);
            return this;
        }

        public Builder addImageLoadModule(@NonNull ImageLoadLibrary imageLoadLibrary, @NonNull IImageLoaderModule iImageLoaderModule) {
            CheckUtils.checkNotNull("ImageLoadLibrary 和 IImageLoaderModule 参数不能为 null.", imageLoadLibrary, iImageLoaderModule);

            // 默认加载框架为第一个添加的框架
            if (defaultImageLoaderModule == null)
                defaultImageLoaderModule = iImageLoaderModule;

            loaderModuleMap.put(imageLoadLibrary, iImageLoaderModule);
            return this;
        }

        /**
         * 配置全局的加载中图片
         *
         * @param loadingRes
         * @return
         */
        public Builder loadingRes(@DrawableRes int loadingRes) {
            this.loadingRes = loadingRes;
            return this;
        }

        /**
         * 配置全局的加载错误图片
         *
         * @param errorRes
         * @return
         */
        public Builder errorRes(@DrawableRes int errorRes) {
            this.errorRes = errorRes;
            return this;
        }

        public ImageModuleConfig build() {
            CheckUtils.checkNotEmpty(loaderModuleMap, "必须调用 defaultImageLoadModule() 或者 addImageLoadModule() 方法指定图片加载框架.");
            return new ImageModuleConfig(this);
        }
    }
}
