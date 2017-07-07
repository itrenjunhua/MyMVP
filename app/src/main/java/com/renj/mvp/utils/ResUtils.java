package com.renj.mvp.utils;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-07-07   11:40
 * <p>
 * 描述：获取res文件夹下的数据
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ResUtils {
    /**
     * 获取资源 Resources
     *
     * @return Resources
     */
    public static Resources getResources() {
        return UIUtils.getContext().getResources();
    }

    /**
     * 根据id获取文字
     *
     * @param resId 文字id
     * @return id表示的文字
     */
    public static String getString(@StringRes int resId) {
        return getResources().getString(resId);
    }

    /**
     * 根据id获取文字数组
     *
     * @param resId 文字数组的id
     * @return id表示的文字数组
     */
    public static String[] getStringArray(@ArrayRes int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 根据id获取dimen
     *
     * @param resId Dimen 的id
     * @return id表示的大小值 单位:px
     */
    public static int getDimens(@DimenRes int resId) {
        return getResources().getDimensionPixelSize(resId);
    }

    /**
     * 根据id获取drawable
     *
     * @param resId Drawable id
     * @return id表示的Drawable对象
     * @see #getDrawable(int, Resources.Theme)
     * @deprecated 过时，建议使用 {@link #getDrawable(int, Resources.Theme)}
     */
    @Deprecated
    public static Drawable getDrawable(@DrawableRes int resId) {
        return getResources().getDrawable(resId);
    }

    /**
     * 根据id获取drawable
     *
     * @param resId Drawable id
     * @param theme 资源主题，可以为 {@code null}
     * @return id表示的Drawable对象
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static Drawable getDrawable(@DrawableRes int resId, @NonNull Resources.Theme theme) {
        return getResources().getDrawable(resId, theme);
    }

    /**
     * 根据id获取颜色
     *
     * @param resId 颜色id
     * @return id表示的颜色
     * @see #getColor(int, Resources.Theme)
     * @deprecated 过时，建议使用 {@link #getColor(int, Resources.Theme)}
     */
    @Deprecated
    public static int getColor(@ColorRes int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 根据id获取颜色
     *
     * @param resId 颜色id
     * @param theme 资源主题，可以为 {@code null}
     * @return id表示的颜色
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static int getColor(@ColorRes int resId, @NonNull Resources.Theme theme) {
        return getResources().getColor(resId, theme);
    }

    /**
     * 根据id获取颜色选择器
     *
     * @param resId 颜色选择器id
     * @return id表示的颜色选择器
     * @see #getColorStateList(int, Resources.Theme)
     * @deprecated 过时，建议使用 {@link #getColorStateList(int, Resources.Theme)}
     */
    @Deprecated
    public static ColorStateList getColorStateList(@ColorRes int resId) {
        return getResources().getColorStateList(resId);
    }

    /**
     * 根据id获取颜色选择器
     *
     * @param resId 颜色选择器id
     * @param theme 资源主题，可以为 {@code null}
     * @return id表示的颜色选择器
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static ColorStateList getColorStateList(@ColorRes int resId, @NonNull Resources.Theme theme) {
        return getResources().getColorStateList(resId, theme);
    }
}
