package com.renj.common.utils;

import android.app.Activity;
import android.support.annotation.NonNull;
import org.jetbrains.annotations.Contract;

import java.util.LinkedList;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2018-12-13   11:28
 * <p>
 * 描述：Activity管理类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ActivityManager {
    private static LinkedList<Activity> activities = new LinkedList<>();

    public static void addActivity(@NonNull Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(@NonNull Activity activity){
        activities.remove(activity);
    }

    @Contract(pure = true)
    public static LinkedList<Activity> getActivities(){
        return activities;
    }
}
