package com.renj.imageloaderlibrary.loader;

import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

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
/*public*/ class Utils {
    private static int winWidth;
    private static int winHeight;

    static void initUtils(Context context) {
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
    static int getWinWidth() {
        return winWidth;
    }

    /**
     * 获取屏幕的高
     *
     * @return 屏幕的高
     */
    @org.jetbrains.annotations.Contract(pure = true)
    static int getWinHeight() {
        return winHeight;
    }
}
