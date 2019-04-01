package com.renj.imageloaderlibrary.config;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.renj.imageloaderlibrary.utils.Utils;

import java.io.File;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2018-03-08   10:32
 * <p>
 * 描述：单个图片加载信息配置类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ImageLoadConfig {
    // ********** 直接将下列对象作为Tag处理，优先级 target > fragmentV4 > fragment > fragmentActivity >activity > context > application ********** //
    private View target; // 图片展示目标控件
    private Context context;
    private Activity activity;
    private FragmentActivity fragmentActivity;
    private Fragment fragment;
    private android.support.v4.app.Fragment fragmentV4;

    /***************图片路径信息********************/
    private String url; // 网络图片路径
    private String filePath; // 文件路径
    private File file; // 文件路径
    @DrawableRes
    private int drawableId;  // 资源id
    private Uri uri; // 图片 uri

    private boolean isSkipMemory; // 是否跳过内存缓存
    private boolean isSkipDisk; // 是否跳过磁盘缓存

    @DrawableRes
    private int errorImageId; // 加载错误时显示的图片id
    @DrawableRes
    private int loadingImageId; // 正在加载时显示的图片id

    private Drawable errorDrawable; // 加载错误时显示的图片，优先级高于 errorImageId
    private Drawable loadingDrawable; // 正在加载时显示的图片，优先级高于 loadingImageId

    @IntRange(from = 0)
    private int width; // 图片宽
    @IntRange(from = 0)
    private int height; // 图片高

    private boolean centerCrop; // 图片完全填充控件，但是可能会被裁剪
    private boolean fitCenter;  // 图片完全显示，但是控件可能留白
    private boolean centerInside; // Center是会保持原图大小，而CenterInside图片的大小是不会超过View的大小的

    // 旋转配置
    private RotateConfig rotateConfig;
    // 圆角配置
    private RoundConfig roundConfig;
    // 是否圆形图片
    private boolean isCircle;

    // ********************  Glide 配置 ******************** //
    private boolean isGif; // 是否 Gif 图片
    private boolean isBitmap; // 是否作为 Bitmap 显示
    @FloatRange(from = 0)
    private float thumbnail; // 缩略图缩放倍数

    protected ImageLoadConfig(Builder builder) {
        this.target = builder.target;
        this.context = builder.context;
        this.activity = builder.activity;
        this.fragmentActivity = builder.fragmentActivity;
        this.fragment = builder.fragment;
        this.fragmentV4 = builder.fragmentV4;
        this.url = builder.url;
        this.filePath = builder.filePath;
        this.file = builder.file;
        this.drawableId = builder.drawableId;
        this.uri = builder.uri;
        this.isSkipMemory = builder.isSkipMemory;
        this.isSkipDisk = builder.isSkipDisk;
        this.errorImageId = builder.errorImageId;
        this.loadingImageId = builder.loadingImageId;
        this.loadingDrawable = builder.loadingDrawable;
        this.errorDrawable = builder.errorDrawable;
        this.width = builder.width;
        this.height = builder.height;
        this.centerCrop = builder.centerCrop;
        this.fitCenter = builder.fitCenter;
        this.centerInside = builder.centerInside;
        this.rotateConfig = builder.rotateConfig;
        this.roundConfig = builder.roundConfig;
        this.isCircle = builder.isCircle;

        this.isGif = builder.isGif;
        this.isBitmap = builder.isBitmap;
        this.thumbnail = builder.thumbnail;
    }

    public View getTarget() {
        return target;
    }

    public Context getContext() {
        return context;
    }

    public Activity getActivity() {
        return activity;
    }

    public FragmentActivity getFragmentActivity() {
        return fragmentActivity;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public android.support.v4.app.Fragment getFragmentV4() {
        return fragmentV4;
    }

    public String getUrl() {
        return url;
    }

    public String getFilePath() {
        return filePath;
    }

    public File getFile() {
        return file;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public Uri getUri() {
        return uri;
    }

    public boolean isSkipMemory() {
        return isSkipMemory;
    }

    public boolean isSkipDisk() {
        return isSkipDisk;
    }

    public int getErrorImageId() {
        return errorImageId;
    }

    public int getLoadingImageId() {
        return loadingImageId;
    }

    public Drawable getErrorDrawable() {
        return errorDrawable;
    }

    public Drawable getLoadingDrawable() {
        return loadingDrawable;
    }

    public RotateConfig getRotateConfig() {
        return rotateConfig;
    }

    public RoundConfig getRoundConfig() {
        return roundConfig;
    }

    public boolean isCircle() {
        return isCircle;
    }

    public int getWidth() {
        if (width <= 0) {
            //先去imageview里取,如果为0,则赋值成matchparent
            if (target != null) {
                width = target.getMeasuredWidth();
            }
            if (width <= 0) {
                width = Utils.getWinWidth();
            }
        }
        return width;
    }

    public int getHeight() {
        if (height <= 0) {
            //先去imageview里取,如果为0,则赋值成matchparent
            if (target != null) {
                height = target.getMeasuredWidth();
            }
            if (height <= 0) {
                height = Utils.getWinHeight();
            }
        }
        return height;
    }

    public boolean isCenterCrop() {
        return centerCrop;
    }

    public boolean isFitCenter() {
        return fitCenter;
    }

    public boolean isCenterInside() {
        return centerInside;
    }

    public boolean isGif() {
        return isGif;
    }

    public boolean isBitmap() {
        return isBitmap;
    }

    public float getThumbnail() {
        return thumbnail;
    }

    public static class Builder {
        private View target; // 图片展示目标控件
        private Context context;
        /*** 当选择Glide框架时，with() 方法可以传递context之外，还可以传递一下参数 ***/
        private Activity activity;
        private FragmentActivity fragmentActivity;
        private Fragment fragment;
        private android.support.v4.app.Fragment fragmentV4;

        /***************图片路径信息********************/
        private String url; // 网络图片路径
        private String filePath; // 文件路径
        private File file; // 文件路径
        @DrawableRes
        private int drawableId;  // 资源id
        private Uri uri; // 图片 uri

        private boolean isSkipMemory; // 是否跳过内存缓存
        private boolean isSkipDisk; // 是否跳过磁盘缓存

        @DrawableRes
        private int errorImageId; // 加载错误时显示的图片id
        @DrawableRes
        private int loadingImageId; // 正在加载时显示的图片id

        private Drawable errorDrawable; // 加载错误时显示的图片，优先级高于 errorImageId
        private Drawable loadingDrawable; // 正在加载时显示的图片，优先级高于 loadingImageId

        @IntRange(from = 0)
        private int width; // 图片宽
        @IntRange(from = 0)
        private int height; // 图片高

        private boolean centerCrop; // 图片完全填充控件，但是可能会被裁剪
        private boolean fitCenter;  // 图片完全显示，但是控件可能留白
        private boolean centerInside; // Center是会保持原图大小，而CenterInside图片的大小是不会超过View的大小的

        // 旋转配置
        private RotateConfig rotateConfig;
        // 圆角配置
        private RoundConfig roundConfig;
        // 是否圆形图片
        private boolean isCircle;

        // ********************  Glide 配置 ******************** //
        private boolean isGif; // 是否 Gif 图片
        private boolean isBitmap; // 是否作为 Bitmap 显示
        @FloatRange(from = 0)
        private float thumbnail; // 缩略图缩放倍数

        public Builder() {
        }

        /**
         * 指定图片显示目标控件
         *
         * @param target 图片显示目标控件
         */
        public <T extends Builder> T target(@NonNull View target) {
            this.target = target;
            return (T) this;
        }

        /**
         * 上下文 {@link Context} 参数
         *
         * @param context {@link Context} 上下文
         * @see #activity(Activity)
         * @see #fragmentActivity(FragmentActivity)
         * @see #fragment(Fragment)
         * @see #fragmentV4(android.support.v4.app.Fragment)
         */
        public <T extends Builder> T context(@NonNull Context context) {
            this.context = context;
            return (T) this;
        }

        /**
         * 指定 {@link Activity} 对象 ，使用 Glide 框架的时候，可以传非 {@link Context} 的其他参数
         *
         * @param activity {@link Activity} 对象
         * @see #fragmentActivity(FragmentActivity)
         * @see #fragment(Fragment)
         * @see #fragmentV4(android.support.v4.app.Fragment)
         */
        public <T extends Builder> T activity(@NonNull Activity activity) {
            this.activity = activity;
            return (T) this;
        }

        /**
         * 指定 {@link FragmentActivity} 对象 ，使用 Glide 框架的时候，可以传非 {@link Context} 的其他参数
         *
         * @param fragmentActivity {@link FragmentActivity} 对象
         * @see #activity(Activity)
         * @see #fragment(Fragment)
         * @see #fragmentV4(android.support.v4.app.Fragment)
         */
        public <T extends Builder> T fragmentActivity(@NonNull FragmentActivity fragmentActivity) {
            this.fragmentActivity = fragmentActivity;
            return (T) this;
        }

        /**
         * 指定 {@link Fragment} 对象 ，使用 Glide 框架的时候，可以传非 {@link Context} 的其他参数
         *
         * @param fragment {@link Fragment} 对象
         * @see #activity(Activity)
         * @see #fragmentActivity(FragmentActivity)
         * @see #fragmentV4(android.support.v4.app.Fragment)
         */
        public <T extends Builder> T fragment(@NonNull Fragment fragment) {
            this.fragment = fragment;
            return (T) this;
        }

        /**
         * 指定 {@link android.support.v4.app.Fragment} 对象 ，使用 Glide 框架的时候，可以传非 {@link Context} 的其他参数
         *
         * @param fragmentV4 {@link android.support.v4.app.Fragment} 对象
         * @see #activity(Activity)
         * @see #fragmentActivity(FragmentActivity)
         * @see #fragment(Fragment)
         */
        public <T extends Builder> T fragmentV4(@NonNull android.support.v4.app.Fragment fragmentV4) {
            this.fragmentV4 = fragmentV4;
            return (T) this;
        }

        /**
         * 指定网络图片路径
         *
         * @param url 网络图片路径
         */
        public <T extends Builder> T url(@NonNull String url) {
            this.url = url;
            return (T) this;
        }

        /**
         * 指定本地图片路径
         *
         * @param filePath 本地图片路径
         */
        public <T extends Builder> T filePath(@NonNull String filePath) {
            this.filePath = filePath;
            return (T) this;
        }

        /**
         * 指定图片文件
         *
         * @param file 图片文件
         */
        public <T extends Builder> T file(@NonNull File file) {
            this.file = file;
            return (T) this;
        }

        /**
         * 指定资源图片id
         *
         * @param drawableId 资源图片id
         */
        public <T extends Builder> T drawableId(@DrawableRes int drawableId) {
            this.drawableId = drawableId;
            return (T) this;
        }

        /**
         * 指定图片 uri
         *
         * @param uri 图片 uri
         */
        public <T extends Builder> T uri(@NonNull Uri uri) {
            this.uri = uri;
            return (T) this;
        }

        /**
         * 跳过内存缓存
         */
        public <T extends Builder> T skipMemoryCache() {
            this.isSkipMemory = true;
            return (T) this;
        }

        /**
         * 跳过磁盘缓存
         */
        public <T extends Builder> T skipDiskCache() {
            this.isSkipDisk = true;
            return (T) this;
        }

        /**
         * 正在加载时显示的图片id，优先级低于 {@link #loadingDrawable(Drawable)}
         *
         * @see #loadingDrawable(Drawable)
         */
        public <T extends Builder> T loadingImageId(@DrawableRes int loadingImageId) {
            this.loadingImageId = loadingImageId;
            return (T) this;
        }

        /**
         * 加载失败时显示的图片id，优先级低于 {@link #errorDrawable(Drawable)}
         *
         * @see #errorDrawable(Drawable)
         */
        public <T extends Builder> T errorImageId(@DrawableRes int errorImageId) {
            this.errorImageId = errorImageId;
            return (T) this;
        }

        /**
         * 正在加载时显示的图片，参数不为 {@code null} 时优先级高于 {@link #loadingImageId(int)}
         *
         * @see #loadingImageId(int)
         */
        public <T extends Builder> T loadingDrawable(Drawable loadingDrawable) {
            this.loadingDrawable = loadingDrawable;
            return (T) this;
        }

        /**
         * 加载失败时显示的图片，参数不为 {@code null} 时优先级高于 {@link #errorImageId(int)}
         *
         * @see #errorImageId(int)
         */
        public <T extends Builder> T errorDrawable(Drawable errorDrawable) {
            this.errorDrawable = errorDrawable;
            return (T) this;
        }

        /**
         * 加载图片的宽
         *
         * @param width 图片的宽
         */
        public <T extends Builder> T width(@IntRange(from = 0) int width) {
            this.width = width;
            return (T) this;
        }

        /**
         * 加载图片的高
         *
         * @param height 图片的高
         */
        public <T extends Builder> T height(@IntRange(from = 0) int height) {
            this.height = height;
            return (T) this;
        }

        /**
         * 设置图片完全填充控件，但是可能会被裁剪
         *
         * @see #fitCenter()
         */
        public <T extends Builder> T centerCrop() {
            centerCrop = true;
            return (T) this;
        }

        /**
         * 设置图片完全显示，但是控件可能留白
         *
         * @see #centerCrop()
         */
        public <T extends Builder> T fitCenter() {
            fitCenter = true;
            return (T) this;
        }

        /**
         * 设置centerInside  Center是会保持原图大小，而CenterInside图片的大小是不会超过View的大小的
         *
         * @see #centerCrop()
         */
        public <T extends Builder> T centerInside() {
            centerInside = true;
            return (T) this;
        }

        /**
         * 设置图片旋转参数
         *
         * @param rotateConfig 旋转参数 {@link RotateConfig}
         * @param <T>
         * @return
         */
        public <T extends Builder> T rotateConfig(RotateConfig rotateConfig) {
            this.rotateConfig = rotateConfig;
            return (T) this;
        }

        /**
         * 设置图片圆角参数
         *
         * @param roundConfig {@link RoundConfig}
         * @param <T>
         * @return
         */
        public <T extends Builder> T roundConfig(RoundConfig roundConfig) {
            this.roundConfig = roundConfig;
            return (T) this;
        }

        /**
         * 设置图片显示圆形
         *
         * @param <T>
         * @return
         */
        public <T extends Builder> T asCircle() {
            this.isCircle = true;
            return (T) this;
        }

        /**
         * 图片作为 gif 图片
         */
        public <T extends Builder> T asGif() {
            this.isGif = true;
            return (T) this;
        }

        /**
         * 将图片转换为 {@link Bitmap}
         */
        public <T extends Builder> T asBitmap() {
            this.isBitmap = true;
            return (T) this;
        }

        /**
         * 指定 缩略图缩放倍数
         *
         * @param thumbnail 缩略图缩放倍数
         */
        public <T extends Builder> T thumbnail(@FloatRange(from = 0) float thumbnail) {
            this.thumbnail = thumbnail;
            return (T) this;
        }

        /**
         * 构建 {@link ImageLoadConfig} 对象
         *
         * @return {@link ImageLoadConfig} 对象
         */
        public <T extends ImageLoadConfig> T build() {
            return (T) new ImageLoadConfig(this);
        }
    }

    /**
     * 旋转参数
     */
    public final static class RotateConfig {
        public float rotateRotationAngle = 0f;
        public float pivotX = 0f, pivotY = 0f;

        /**
         * 构造
         *
         * @param rotateRotationAngle 旋转角度，默认旋转点(0,0)
         */
        public RotateConfig(float rotateRotationAngle) {
            this.rotateRotationAngle = rotateRotationAngle;
        }

        /**
         * 构造
         *
         * @param rotateRotationAngle 旋转角度
         * @param pivotPoint          旋转点
         */
        public RotateConfig(float rotateRotationAngle, float pivotPoint) {
            this.rotateRotationAngle = rotateRotationAngle;
            this.pivotX = pivotPoint;
            this.pivotY = pivotPoint;
        }

        /**
         * 构造
         *
         * @param rotateRotationAngle 旋转角度
         * @param pivotX              旋转点横坐标
         * @param pivotY              旋转点纵坐标
         */
        public RotateConfig(float rotateRotationAngle, float pivotX, float pivotY) {
            this.rotateRotationAngle = rotateRotationAngle;
            this.pivotX = pivotX;
            this.pivotY = pivotY;
        }
    }

    /**
     * 圆角参数，默认 4
     */
    public final static class RoundConfig {
        public int radiusX, radiusY;

        /**
         * 构造
         *
         * @see #RoundConfig(int)
         * @see #RoundConfig(int, int)
         */
        public RoundConfig() {
            this(4, 4);
        }

        /**
         * 构造，指定圆角大小，默认 4
         *
         * @param radius 圆角大小
         * @see #RoundConfig()
         * @see #RoundConfig(int, int)
         */
        public RoundConfig(int radius) {
            this(radius, radius);
        }

        /**
         * 构造，指定x轴方向和y轴方向的圆角大小，默认 4
         *
         * @param radiusX x方向圆角大小
         * @param radiusY y方向圆角大小
         * @see #RoundConfig()
         * @see #RoundConfig(int)
         */
        public RoundConfig(int radiusX, int radiusY) {
            this.radiusX = radiusX;
            this.radiusY = radiusY;
        }
    }
}
