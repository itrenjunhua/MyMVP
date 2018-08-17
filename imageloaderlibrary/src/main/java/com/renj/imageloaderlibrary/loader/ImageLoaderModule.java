package com.renj.imageloaderlibrary.loader;

import android.app.Application;
import android.support.annotation.NonNull;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2018-03-08   10:47
 * <p>
 * 描述：使用二次封装后的图片框架入口类
 * <br/><br/>
 * 说明：<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * 为了应对不同的框架之间的差别，在顶层接口 {@link IImageLoaderModule} 中只定义了最基本的加载方法，如果需要使用单个加载框架的特殊方法时，
 * 需要定义接口【如 IGlideLoaderModule】 继承 {@link IImageLoaderModule} 接口，在子类中实现具体方法，使用到了泛型。
 * <b>因此，在项目中使用时推荐建立一个加载框架管理类【如 ImageLoaderManager】操作该类中的方法，避免在上层代码中直接使用 {@link ImageLoaderModule} 中的方法，
 * 因为在 {@link ImageLoaderModule} 中的方法使用了泛型。</b>
 * <p>
 * 推荐使用方法：<br/>
 * <pre><code>
 * public class ImageLoaderManager {
 *  public static void init(@NonNull Application application) {
 *      ImageLoaderModule.initImageLoaderModule(application, new GlideLoaderModule());
 *  }
 *
 *  public static GlideLoaderModule getImageLoader() {
 *      return ImageLoaderModule.getImageLoaderModule();
 *  }
 * }
 *
 * // 在 {@link Application} 类中初始化
 * ImageLoaderManager.init(this);
 *
 * // 在类中加载图片使用：
 * ImageLoaderManager.getImageLoader().loadImage({@link ImageInfoConfig});
 * </code></pre>
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ImageLoaderModule {
    public static ImageLoaderHelper imageLoaderHelper;

    private ImageLoaderModule() {
    }

    /**
     * 初始化图片加载库，只需要在启动应用时调用一次即可
     *
     * @param application        {@link Application} 对象
     * @param iImageLoaderModule {@link IImageLoaderModule} 子类对象实例
     * @param <T>                {@link IImageLoaderModule} 对象
     */
    @org.jetbrains.annotations.Contract("null,null -> fail; _,null -> fail; null,_ -> fail")
    public static <T extends IImageLoaderModule> void initImageLoaderModule(@NonNull Application application, @NonNull T iImageLoaderModule) {
        if (imageLoaderHelper == null) {
            synchronized (ImageLoaderModule.class) {
                if (imageLoaderHelper == null) {
                    if (iImageLoaderModule == null || application == null)
                        throw new NullPointerException("initImageLoaderModule() 方法参数不能为 null");

                    imageLoaderHelper = new ImageLoaderHelper<T>();
                    imageLoaderHelper.initImageLoaderUtils(application, iImageLoaderModule);
                }
            }
        }
    }

    /**
     * 获取 {@link IImageLoaderModule} 子类对象实例
     *
     * @param <T> {@link IImageLoaderModule} 对象
     * @return {@link IImageLoaderModule} 子类对象实例
     */
    @org.jetbrains.annotations.Contract(pure = true)
    public static <T extends IImageLoaderModule> T getImageLoaderModule() {
        if (imageLoaderHelper == null || imageLoaderHelper.getImageLoaderModule() == null)
            throw new IllegalStateException("没有调用 ImageLoaderModule.initImageLoaderModule(IImageLoaderModule) 方法进行初始化");

        return (T) imageLoaderHelper.getImageLoaderModule();
    }
}
