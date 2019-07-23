package com.renj.utils.common;

import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;
import com.renj.utils.AndroidUtils;
import com.renj.utils.res.ResUtils;

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
    @org.jetbrains.annotations.Contract(pure = true)
    public static Context getContext() {
        return AndroidUtils.getApplication();
    }

    /**
     * dip转换成px
     *
     * @param dipValue
     * @return px值
     */
    public static int dip2px(float dipValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px转换成dip
     *
     * @param pxValue
     * @return dp值
     */
    public static int px2dip(float pxValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转换成px
     */
    public static int sp2px(float spValue) {
        float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px转换成sp
     */
    public static int px2sp(float pxValue) {
        float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 获取屏幕的宽
     *
     * @return 屏幕的宽
     */
    public static int getScreenWidth() {
        Point point = getScreenPoint();
        return point.x;
    }

    /**
     * 获取屏幕的高
     *
     * @return 屏幕的高
     */
    public static int getScreenHeight() {
        Point point = getScreenPoint();
        return point.y;
    }

    /**
     * 通过 {@link WindowManager} 获取屏幕宽高信息
     *
     * @return 包含屏幕宽和高信息的 {@link Point}
     */
    @NonNull
    private static Point getScreenPoint() {
        WindowManager windowManager = (WindowManager) UIUtils.getContext().getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        windowManager.getDefaultDisplay().getRealSize(point);
        return point;
    }

    /**
     * 获取主线程的{@link Handler}
     *
     * @return 主线程的Handler
     */
    @org.jetbrains.annotations.Contract(pure = true)
    public static Handler getHandler() {
        return new Handler(Looper.getMainLooper());
    }

    /**
     * 获取主线程的 {@link Looper}
     *
     * @return 主线程的 Looper
     */
    public static Looper getMainLooper() {
        return Looper.getMainLooper();
    }

    /**
     * 获取主线对象
     *
     * @return 主线程 Thread
     */
    @NonNull
    public static Thread getMainThread() {
        return getMainLooper().getThread();
    }

    /**
     * 判断当前的线程是不是在主线程
     *
     * @return true：是主线程
     */
    public static boolean isRunInMainThread() {
        return Thread.currentThread().getId() == getMainThread().getId();
    }

    /**
     * 运行在新的线程中
     *
     * @param runnable 需要运行在新线程的 {@link Runnable}
     */
    public static void runOnNewThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.start();
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
    public static void showToastSafe(@StringRes int resId) {
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
        if (null != getContext()) {
            if (null == mToast) {
                mToast = Toast.makeText(getContext(), str, Toast.LENGTH_SHORT);
                mToast.setGravity(Gravity.CENTER, 0, 0);
            }
            mToast.setText(str);
            mToast.show();
        }
    }
}
