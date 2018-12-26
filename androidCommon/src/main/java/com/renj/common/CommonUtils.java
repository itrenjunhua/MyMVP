package com.renj.common;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2018-11-13   10:28
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class CommonUtils {
    private static Application application;

    public static void init(Application application) {
        CommonUtils.application = application;
    }

    public static Context getApplication() {
        if (application == null)
            throw new NullPointerException("请先调用 com.renj.common.CommonUtils.init(Application application) 方法进行初始化");
        return application;
    }

    public static boolean isDebug() {
        if (application == null)
            throw new NullPointerException("请先调用 com.renj.common.CommonUtils.init(Application application) 方法进行初始化");

        boolean isDebug = application.getApplicationInfo() != null &&
                (application.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        return isDebug;
    }
}
