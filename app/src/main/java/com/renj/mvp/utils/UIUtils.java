package com.renj.mvp.utils;

import android.content.Context;

import com.renj.mvp.app.MyApplication;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-07-06   18:09
 * <p>
 * 描述：与界面相关的工具类，包含获取全局的Context，单位转换等
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
        return MyApplication.applicationComponent.getApplication();
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
}
