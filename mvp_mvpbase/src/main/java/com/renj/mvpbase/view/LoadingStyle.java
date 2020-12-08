package com.renj.mvpbase.view;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-06-26   13:46
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({
        LoadingStyle.LOADING_PAGE,
        LoadingStyle.LOADING_DIALOG,
        LoadingStyle.LOADING_REFRESH,
        LoadingStyle.LOADING_LOAD_MORE,
        LoadingStyle.LOADING_CUSTOM,
        LoadingStyle.LOADING_NONE
})
public @interface LoadingStyle {
    /**
     * 加载样式为显示加载页面
     */
    int LOADING_PAGE = 11;
    /**
     * 加载样式为显示dialog加载框
     */
    int LOADING_DIALOG = 12;
    /**
     * 下拉刷新
     */
    int LOADING_REFRESH = 13;
    /**
     * 上拉加载
     */
    int LOADING_LOAD_MORE = 14;
    /**
     * 显示自定义加载状态
     */
    int LOADING_CUSTOM = 15;
    /**
     * 不显示加载状态，直接获取数据
     */
    int LOADING_NONE = -1;
}
