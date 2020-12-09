package com.renj.utils.common;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.renj.utils.res.StringUtils;


/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-07-06   18:08
 * <p>
 * 描述：Package工具类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class PackageUtils {
    /**
     * 获取应用包名
     *
     * @return 包名
     */
    @NonNull
    @CheckResult(suggest = "返回结果没有使用过")
    public static String getPackageName() {
        return UIUtils.getContext().getPackageName();
    }

    /**
     * 根据包名(packageName)获取packageInfo
     *
     * @param packageName 包名
     * @return PackageInfo对象，获取失败返回null
     */
    @Nullable
    @CheckResult(suggest = "返回结果没有使用过")
    public static PackageInfo getPackageInfo(String packageName) {
        Context context = UIUtils.getContext();
        if (null == context) {
            return null;
        }
        if (StringUtils.isEmpty(packageName)) {
            packageName = getPackageName();
        }
        PackageInfo info = null;
        PackageManager manager = context.getPackageManager();
        try {
            // 根据packageName获取packageInfo
            // GET_UNINSTALLED_PACKAGES代表已删除，但还有安装目录的
            info = manager.getPackageInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
        } catch (PackageManager.NameNotFoundException e) {
            Logger.e("Exception Info: " + e);
        }
        return info;
    }

    /**
     * 获取本应用的版本号 VersionCode
     *
     * @return 返回版本号，-1表示失败
     */
    @CheckResult(suggest = "返回结果没有使用过")
    public static int getVersionCode() {
        PackageInfo info = getPackageInfo(null);
        if (info != null) {
            return info.versionCode;
        } else {
            return -1;
        }
    }

    /**
     * 获取本应用的版本名 VersionName
     *
     * @return 应用版本名，null 表示失败
     */
    @Nullable
    public static String getVersionName() {
        PackageInfo info = getPackageInfo(null);
        if (info != null) {
            return info.versionName;
        } else {
            return null;
        }
    }

    /**
     * 根据包名判断是否是第三方软件
     *
     * @param packageName 包名
     * @return true：是第三方软件
     */
    public static boolean isThirdPartyApp(@NonNull String packageName) {
        Context context = UIUtils.getContext();
        if (null == context) {
            return false;
        }
        PackageManager pm = context.getPackageManager();
        PackageInfo packageInfo;
        try {
            packageInfo = pm.getPackageInfo(packageName, 0);
            return isThirdPartyApp(packageInfo);
        } catch (PackageManager.NameNotFoundException e) {
            Logger.e("Exception Info: " + e);
            return false;
        }
    }

    /**
     * 通过 PackageInfo 判断是否是第三方软件
     *
     * @param packageInfo PackageInfo对象
     * @return true：是第三方软件
     */
    public static boolean isThirdPartyApp(@NonNull PackageInfo packageInfo) {
        if (null == packageInfo || null == packageInfo.applicationInfo) {
            return false;
        }
        return ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) || ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0);
    }
}
