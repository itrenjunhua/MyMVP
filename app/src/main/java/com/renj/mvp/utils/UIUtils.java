package com.renj.mvp.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.renj.mvp.app.MyApplication;
import com.renj.mvp.base.BaseActivity;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-07-06   18:09
 * <p>
 * 描述：与界面以及UI线程相关的工具类，包含获取全局的Context，单位转换；<br/>
 * 自主线程执行Runnable、获取主线程对象、MainHandler、MainLooper、弹出Toast等
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class UIUtils {
    /**
     * 获取全局的上下文
     *
     * @return 全局的上下文
     */
    public static Context getContext() {
        return MyApplication.mApplicationComponent.getApplication();
    }

    /**
     * dip转换成px
     *
     * @param dip
     * @return px值
     */
    public static int dip2px(int dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * px转换成dip
     *
     * @param px
     * @return dp值
     */
    public static int px2dip(int px) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 获取主线程的{@link Handler}
     *
     * @return 主线程的Handler
     */
    @org.jetbrains.annotations.Contract(pure = true)
    public static Handler getHandler() {
        return MyApplication.getMainThreadHandler();
    }

    /**
     * 获取主线程的 {@link Looper}
     *
     * @return 主线程的 Looper
     */
    public static Looper getMainLooper() {
        return MyApplication.mApplicationComponent.getMainLooper();
    }

    /**
     * 获取主线对象
     *
     * @return 主线程 Thread
     */
    public static Thread getMainThread() {
        return MyApplication.mApplicationComponent.getMainThread();
    }

    /**
     * 判断当前的线程是不是在主线程
     *
     * @return true：是主线程
     */
    public static boolean isRunInMainThread() {
        return android.os.Process.myTid() == getMainThread().getId();
    }

    /**
     * 延时在主线程执行{@link Runnable}
     *
     * @param runnable    需要执行的 {@link Runnable}
     * @param delayMillis 延迟时间
     * @return 是否执行成功 true：成功
     */
    public static boolean postDelayed(Runnable runnable, long delayMillis) {
        return getHandler().postDelayed(runnable, delayMillis);
    }

    /**
     * 在主线程执行{@link Runnable}
     *
     * @param runnable 需要执行的 {@link Runnable}
     * @return 是否执行成功 true：成功
     */
    public static boolean post(Runnable runnable) {
        return getHandler().post(runnable);
    }

    /**
     * 从主线程looper里面移除{@link Runnable}
     *
     * @param runnable 需要移出的 {@link Runnable}
     */
    public static void removeCallbacks(Runnable runnable) {
        getHandler().removeCallbacks(runnable);
    }

    /**
     * 对toast的简易封装。线程安全，可以在非UI线程调用。
     *
     * @param resId 显示信息的资源id
     */
    public static void showToastSafe(final int resId) {
        showToastSafe(ResUtils.getString(resId));
    }

    /**
     * 对toast的简易封装。线程安全，可以在非UI线程调用。
     *
     * @param str 现实的信息
     */
    public static void showToastSafe(final String str) {
        if (isRunInMainThread()) {
            showToast(str);
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    showToast(str);
                }
            });
        }
    }

    private static Toast mToast;

    /**
     * 显示单例Toast
     *
     * @param str
     */
    private static void showToast(String str) {
        BaseActivity frontActivity = BaseActivity.getForegroundActivity();
        if (null != frontActivity) {
            if (null == mToast)
                mToast = Toast.makeText(frontActivity, str, Toast.LENGTH_LONG);
            mToast.setText(str);
            mToast.show();
        }
    }
}
