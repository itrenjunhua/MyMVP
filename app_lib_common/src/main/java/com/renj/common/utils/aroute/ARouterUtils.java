package com.renj.common.utils.aroute;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-07-23   16:36
 * <p>
 * 描述：ARouter 工具类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ARouterUtils {
    public static void openActivity(String path) {
        ARouter.getInstance().build(path).navigation();
    }

    public static void openActivity(String path, String group) {
        ARouter.getInstance().build(path, group).navigation();
    }

    public static void openActivity(Uri uri) {
        ARouter.getInstance().build(uri).navigation();
    }

    public static void openActivity(String path, String key, Parcelable parcelable) {
        ARouter.getInstance().build(path).withParcelable(key, parcelable).navigation();
    }

    public static void openActivity(String path, String key, Bundle bundle) {
        ARouter.getInstance().build(path).withBundle(key, bundle).navigation();
    }

    public static void openActivity(String path, String group, String key, Parcelable parcelable) {
        ARouter.getInstance().build(path, group).withParcelable(key, parcelable).navigation();
    }

    public static void openActivity(Uri uri, String key, Parcelable parcelable) {
        ARouter.getInstance().build(uri).withParcelable(key, parcelable).navigation();
    }

    public static Fragment getFragment(String path) {
        return (Fragment) ARouter.getInstance().build(path).navigation();
    }

    public static Fragment getFragment(String path, String group) {
        return (Fragment) ARouter.getInstance().build(path, group).navigation();
    }

    public static Fragment getFragment(Uri uri) {
        return (Fragment) ARouter.getInstance().build(uri).navigation();
    }
}
