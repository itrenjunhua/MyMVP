package com.renj.imageloaderlibrary.loader;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import java.io.File;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2018-03-08   10:32
 * <p>
 * 描述：加载图片信息配置基类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ImageInfoConfig {
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
    private int errorImageId; // 加载错误时显示的图片
    @DrawableRes
    private int loadingImageId; // 正在加载时显示的图片

    @IntRange(from = 0)
    private int width; // 图片宽
    @IntRange(from = 0)
    private int height; // 图片高

    protected ImageInfoConfig(Builder builder) {
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
        this.width = builder.width;
        this.height = builder.height;
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
        private int errorImageId; // 加载错误时显示的图片
        @DrawableRes
        private int loadingImageId; // 正在加载时显示的图片

        @IntRange(from = 0)
        private int width; // 图片宽
        @IntRange(from = 0)
        private int height; // 图片高

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
         * 正在加载时显示的图片
         */
        public <T extends Builder> T loadingImageId(@DrawableRes int loadingImageId) {
            this.loadingImageId = loadingImageId;
            return (T) this;
        }

        /**
         * 加载失败时显示的图片
         */
        public <T extends Builder> T errorImageId(@DrawableRes int errorImageId) {
            this.errorImageId = errorImageId;
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
         * 构建 {@link ImageInfoConfig} 对象
         *
         * @return {@link ImageInfoConfig} 对象
         */
        public <T extends ImageInfoConfig> T build() {
            return (T) new ImageInfoConfig(this);
        }
    }
}
