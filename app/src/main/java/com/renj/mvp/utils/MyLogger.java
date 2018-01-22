package com.renj.mvp.utils;

import android.util.Log;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-07-06   15:09
 * <p>
 * 描述：自定义日志打印工具类，支持设置Tag，打印消息的级别和是否打印全类名(类的全路径名)<br/>
 * 打印形式为：(全)类名.方法名(所在行数): 打印的信息
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class MyLogger {
    /**
     * Log日子的 Tag，默认 MyLogger
     */
    private static String TAG = "MyLogger";
    /**
     * 是否打印全部类名(类的全路径名)，默认false
     */
    private static boolean IS_FULL_CLASSNAME;

    /**
     * 是否 debug 版本，true 是调试版本；false 是正式版本
     */
    private static boolean isDebug = AppConfig.IS_DEBUG;

    /**
     * 设置是否打印类的全路径名
     *
     * @param isFullClassName true：打印类的全路径名
     */
    public static void isFullClassName(boolean isFullClassName) {
        MyLogger.IS_FULL_CLASSNAME = isFullClassName;
    }

    /**
     * 设置Log的Tag，默认 "MyLogger"
     *
     * @param tag
     */
    public static void setAppTAG(String tag) {
        MyLogger.TAG = tag;
    }


    public static void v(String msg) {
        if (isDebug) {
            Log.v(TAG, getLogTitle() + msg);
        }
    }


    public static void d(String msg) {
        if (isDebug) {
            Log.d(TAG, getLogTitle() + msg);
        }
    }

    public static void i(String msg) {
        if (isDebug) {
            Log.i(TAG, getLogTitle() + msg);
        }
    }

    public static void w(String msg) {
        if (isDebug) {
            Log.w(TAG, getLogTitle() + msg);
        }
    }

    public static void e(String msg) {
        if (isDebug) {
            Log.e(TAG, getLogTitle() + msg);
        }
    }

    /**
     * 返回类名(根据是否设置了打印全类名返回响应的值)，方法名和日子打印所在行数
     *
     * @return (全)类名.方法名(所在行数):
     */
    @org.jetbrains.annotations.NotNull
    private static String getLogTitle() {
        StackTraceElement elm = Thread.currentThread().getStackTrace()[4];
        String className = elm.getClassName();
        if (!IS_FULL_CLASSNAME) {
            int dot = className.lastIndexOf('.');
            if (dot != -1) {
                className = className.substring(dot + 1);
            }
        }
        return className + "." + elm.getMethodName() + "(" + elm.getLineNumber() + ")" + ": ";
    }
}
