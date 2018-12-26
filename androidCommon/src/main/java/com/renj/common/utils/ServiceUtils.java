package com.renj.common.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2018-06-22   14:27
 * <p>
 * 描述：服务相关工具类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ServiceUtils {
    /**
     * 判断一个服务是否正在运行
     *
     * @param className 服务的name
     * @return
     */
    public static boolean isServiceRunning(String className) {

        ActivityManager activityManager = (ActivityManager) UIUtils.getContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(100);

        if (serviceList.size() <= 0) {
            return false;
        }
        Logger.i("className => " + className);
        for (int i = 0; i < serviceList.size(); i++) {
            Logger.i("serviceName：" + serviceList.get(i).service.getClassName());
            if (serviceList.get(i).service.getClassName().contains(className)) {
                return true;
            }
        }
        return false;
    }
}
