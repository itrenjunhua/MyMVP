package com.renj.imageloaderlibrary.utils;

import android.content.Context;
import android.graphics.Point;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2018-05-07   16:09
 * <p>
 * 描述：常用方法工具类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class Utils {
    private static int winWidth;
    private static int winHeight;

    public static void initUtils(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        wm.getDefaultDisplay().getRealSize(point);
        winWidth = point.x;
        winHeight = point.y;
    }

    /**
     * 获取屏幕的宽
     *
     * @return 屏幕的宽
     */
    @org.jetbrains.annotations.Contract(pure = true)
    public static int getWinWidth() {
        return winWidth;
    }

    /**
     * 获取屏幕的高
     *
     * @return 屏幕的高
     */
    @org.jetbrains.annotations.Contract(pure = true)
    public static int getWinHeight() {
        return winHeight;
    }

    @NonNull
    @SuppressWarnings("UseBulkOperation")
    public static <T> List<T> getSnapshot(@NonNull Collection<T> other) {
        List<T> result = new ArrayList<>(other.size());
        for (T item : other) {
            if (item != null) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * Throws an {@link java.lang.IllegalArgumentException} if called on a thread other than the main
     * thread.
     */
    public static void assertMainThread() {
        if (!isOnMainThread()) {
            throw new IllegalArgumentException("You must call this method on the main thread");
        }
    }

    /**
     * Throws an {@link java.lang.IllegalArgumentException} if called on the main thread.
     */
    public static void assertBackgroundThread() {
        if (!isOnBackgroundThread()) {
            throw new IllegalArgumentException("You must call this method on a background thread");
        }
    }

    /**
     * Returns {@code true} if called on the main thread, {@code false} otherwise.
     */
    public static boolean isOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    /**
     * Returns {@code true} if called on a background thread, {@code false} otherwise.
     */
    public static boolean isOnBackgroundThread() {
        return !isOnMainThread();
    }
}
