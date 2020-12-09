package com.renj.glide;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestOptions;
import com.renj.glide.transform.CircleTransformation;
import com.renj.glide.transform.RotateTransformation;
import com.renj.glide.transform.RoundTransformation;
import com.renj.imageloaderlibrary.config.ImageLoadConfig;
import com.renj.imageloaderlibrary.config.ImageModuleConfig;
import com.renj.imageloaderlibrary.loader.IImageLoaderModule;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2018-03-08   15:06
 * <p>
 * 描述：使用Glide框架加载图片
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class GlideLoaderModule implements IImageLoaderModule {
    private ImageModuleConfig imageModuleConfig;

    @Override
    public void init(@NonNull ImageModuleConfig imageModuleConfig) {
        this.imageModuleConfig = imageModuleConfig;
    }

    @Override
    public void loadImage(@NonNull String url, @NonNull ImageView imageView) {
        ImageLoadConfig imageLoadConfig = new ImageLoadConfig.Builder()
                .url(url)
                .target(imageView)
                .build();
        loadImage(imageLoadConfig);
    }

    @Override
    public <T extends ImageLoadConfig> void loadImage(@NonNull T imageInfoConfig) {
        RequestManager requestManager = createRequestManager(imageInfoConfig);

        if (imageInfoConfig.isBitmap()) {
            RequestBuilder<Bitmap> bitmapRequestBuilder = requestManager.asBitmap();
            RequestBuilder<Bitmap> requestBuilder = loadPath(bitmapRequestBuilder, imageInfoConfig);
            builderControl(requestBuilder, imageInfoConfig);
        } else if (imageInfoConfig.isGif()) {
            RequestBuilder<GifDrawable> gifDrawableRequestBuilder = requestManager.asGif();
            RequestBuilder<GifDrawable> requestBuilder = loadPath(gifDrawableRequestBuilder, imageInfoConfig);
            builderControl(requestBuilder, imageInfoConfig);
        } else {
            RequestBuilder<Drawable> drawableRequestBuilder = requestManager.asDrawable();
            RequestBuilder<Drawable> requestBuilder = loadPath(drawableRequestBuilder, imageInfoConfig);
            builderControl(requestBuilder, imageInfoConfig);
        }
    }

    private <T> void builderControl(RequestBuilder<T> requestBuilder, @NonNull ImageLoadConfig imageLoadConfig) {
        requestBuilder = initImageInfoConfig(requestBuilder, imageLoadConfig);
        intoOf(requestBuilder, imageLoadConfig);
    }

    /**
     * 加载图片到指定控件
     */
    private <T> void intoOf(RequestBuilder<T> requestBuilder, @NonNull ImageLoadConfig imageLoadConfig) {
        if (imageLoadConfig.getTarget() instanceof ImageView) {
            requestBuilder.into((ImageView) imageLoadConfig.getTarget());
        }
    }

    /**
     * 配置图片信息到 Glide 请求中
     */
    @NonNull
    private <T> RequestBuilder<T> initImageInfoConfig(RequestBuilder<T> requestBuilder, @NonNull ImageLoadConfig imageLoadConfig) {
        RequestOptions requestOptions = new RequestOptions();
        if (imageLoadConfig.getWidth() > 0 && imageLoadConfig.getHeight() > 0)
            requestOptions = requestOptions.override(imageLoadConfig.getWidth(), imageLoadConfig.getHeight());
        if (imageLoadConfig.getWidth() > 0 && imageLoadConfig.getHeight() <= 0)
            requestOptions = requestOptions.override(imageLoadConfig.getWidth());
        if (imageLoadConfig.getHeight() > 0 && imageLoadConfig.getWidth() <= 0)
            requestOptions = requestOptions.override(imageLoadConfig.getHeight());

        requestOptions = requestOptions.skipMemoryCache(imageLoadConfig.isSkipMemory());
        requestOptions = requestOptions.diskCacheStrategy(imageLoadConfig.isSkipDisk() ? DiskCacheStrategy.NONE : DiskCacheStrategy.AUTOMATIC);

        if (imageModuleConfig.getLoadingRes() > 0)
            requestOptions = requestOptions.error(imageModuleConfig.getLoadingRes());
        if (imageModuleConfig.getErrorRes() > 0)
            requestOptions = requestOptions.placeholder(imageModuleConfig.getErrorRes());

        if (imageLoadConfig.getErrorImageId() > 0)
            requestOptions = requestOptions.error(imageLoadConfig.getErrorImageId());
        if (imageLoadConfig.getLoadingImageId() > 0)
            requestOptions = requestOptions.placeholder(imageLoadConfig.getLoadingImageId());

        if (imageLoadConfig.getErrorDrawable() != null)
            requestOptions = requestOptions.error(imageLoadConfig.getErrorDrawable());
        if (imageLoadConfig.getLoadingDrawable() != null)
            requestOptions = requestOptions.placeholder(imageLoadConfig.getLoadingDrawable());

        if (imageLoadConfig.isCenterCrop())
            requestOptions = requestOptions.centerCrop();
        if (imageLoadConfig.isFitCenter())
            requestOptions = requestOptions.fitCenter();
        if (imageLoadConfig.isCenterInside())
            requestOptions = requestOptions.centerInside();
        if (imageLoadConfig.getRoundConfig() != null)
            requestOptions = requestOptions.transform(new RoundTransformation(imageLoadConfig.getRoundConfig().radiusX, imageLoadConfig.getRoundConfig().radiusY));
        if (imageLoadConfig.getRotateConfig() != null)
            requestOptions = requestOptions.transform(new RotateTransformation(imageLoadConfig.getRotateConfig().rotateRotationAngle, imageLoadConfig.getRotateConfig().pivotX, imageLoadConfig.getRotateConfig().pivotY));
        if (imageLoadConfig.isCircle())
            requestOptions = requestOptions.transform(new CircleTransformation());

        if (imageLoadConfig.getThumbnail() > 0)
            requestBuilder.thumbnail(imageLoadConfig.getThumbnail());

        return requestBuilder.apply(requestOptions);
    }

    /**
     * 确定图片加载路径
     */
    @NonNull
    private <T> RequestBuilder<T> loadPath(RequestBuilder<T> requestBuilder, @NonNull ImageLoadConfig imageLoadConfig) {
        if (imageLoadConfig.getDrawableId() > 0)
            return requestBuilder.load(imageLoadConfig.getDrawableId());

        if (imageLoadConfig.getUri() != null)
            return requestBuilder.load(imageLoadConfig.getUri());

        if (imageLoadConfig.getFilePath() != null)
            return requestBuilder.load(imageLoadConfig.getFilePath());

        if (imageLoadConfig.getFile() != null)
            return requestBuilder.load(imageLoadConfig.getFile());

        // imageLoadConfig.getUrl() 也可能为 null
        return requestBuilder.load(imageLoadConfig.getUrl());
    }

    /**
     * 创建 RequestManager 对象
     */
    @NonNull
    private RequestManager createRequestManager(@NonNull ImageLoadConfig imageLoadConfig) {

        if (imageLoadConfig.getFragmentV4() != null)
            return GlideApp.with(imageLoadConfig.getFragmentV4());

        if (imageLoadConfig.getFragment() != null)
            return GlideApp.with(imageLoadConfig.getFragment());

        if (imageLoadConfig.getFragmentActivity() != null)
            return GlideApp.with(imageLoadConfig.getFragmentActivity());

        if (imageLoadConfig.getActivity() != null)
            return GlideApp.with(imageLoadConfig.getActivity());

        if (imageLoadConfig.getTarget() != null)
            return GlideApp.with(imageLoadConfig.getTarget());

        if (imageLoadConfig.getContext() != null)
            return GlideApp.with(imageLoadConfig.getContext());

        if (imageModuleConfig.getApplication() != null)
            return GlideApp.with(imageModuleConfig.getApplication());

        throw new NullPointerException("Glide 获取不到 Context");
    }

    @Override
    public void pause() {
        GlideApp.with(imageModuleConfig.getApplication()).pauseRequestsRecursive();
    }

    @Override
    public void resume() {
        GlideApp.with(imageModuleConfig.getApplication()).resumeRequestsRecursive();
    }

    @Override
    public void clearMemoryCache() {
        GlideApp.get(imageModuleConfig.getApplication()).clearMemory();
    }

    @Override
    public void clearDiskCache() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 必须在子线程中调用
                GlideApp.get(imageModuleConfig.getApplication()).clearDiskCache();
            }
        });
        thread.start();
    }
}
