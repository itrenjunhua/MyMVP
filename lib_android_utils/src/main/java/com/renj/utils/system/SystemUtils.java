package com.renj.utils.system;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.renj.utils.common.PackageUtils;
import com.renj.utils.common.UIUtils;

import java.io.File;
import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：itrenjunhua@163.com
 * <p>
 * 创建时间：2018-04-04   14:07
 * <p>
 * 描述：打开系统界面，获取系统信息等相关工具类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class SystemUtils {
    /**
     * 打开系统设置界面
     */
    public static void openSettingActivity(@NonNull String packageName) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", packageName, null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", packageName);
        }
        UIUtils.getContext().startActivity(localIntent);
    }

    /**
     * 打开系统网络设置页面
     */
    public static void openNetWorkActivity() {
        Intent intent = new Intent("android.settings.WIRELESS_SETTINGS");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        UIUtils.getContext().startActivity(intent);
    }

    /**
     * 重启APP
     */
    public static void restartAPP() {
        Intent intent = UIUtils.getContext().getPackageManager().getLaunchIntentForPackage(PackageUtils.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        UIUtils.getContext().startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 根据packageName 判断是否安装某应用
     *
     * @param packageName 需要判断的包名
     */
    public static boolean isAppInstalled(String packageName) {
        final PackageManager packageManager = UIUtils.getContext().getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> pInfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pInfo.size(); i++) {
            String pName = pInfo.get(i).packageName;
            if (TextUtils.equals(packageName, pName)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 检查apk安装包是否可以安装
     *
     * @param apkFilePath apk 文件路径
     */
    public static boolean isApkCanInstall(String apkFilePath) {
        try {
            PackageManager pm = UIUtils.getContext().getPackageManager();
            if (pm != null) {
                PackageInfo info = pm.getPackageArchiveInfo(apkFilePath, PackageManager.GET_ACTIVITIES);
                return info != null;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * 安装apk
     *
     * @param authority   主机地址，Android 7.0 权限适配需要，文件需要转换为 URI 进行访问(Android 7.0 以下可传 null)
     * @param apkFilePath apk 文件路径
     */
    public static void installApk(String authority, String apkFilePath) {
        File apkFile = new File(apkFilePath);
        if (!apkFile.exists()) {
            UIUtils.showToast("安装文件不存在!");
            return;
        }
        if (!isApkCanInstall(apkFilePath)) {
            UIUtils.showToast("安装失败!");
            apkFile.deleteOnExit();
            return;
        }
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(getProvider(authority, apkFilePath), "application/vnd.android.package-archive");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            UIUtils.getContext().startActivity(intent);
        } catch (Exception e) {
            UIUtils.showToast("安装失败!");
            e.printStackTrace();
        }
    }

    /**
     * 将路径转换为URI
     *
     * @param authority 主机地址，Android 7.0 权限适配需要，文件需要转换为 URI 进行访问
     * @param path      转换路径
     * @return Uri
     */
    public static Uri getProvider(String authority, String path) {
        if (TextUtils.isEmpty(path)) return null;
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(UIUtils.getContext(), authority, new File(path));
        } else {
            uri = Uri.fromFile(new File(path));
        }
        return uri;
    }

    /**
     * 判断一个应用是否正在运行
     *
     * @param packageName 包名
     * @return true：正在运行状态 false：未运行状态
     */
    public static boolean isRunning(String packageName) {
        ActivityManager am = (ActivityManager) UIUtils.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infos = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo rapi : infos) {
            if (rapi.processName.equals(packageName))
                return true;
        }
        return false;
    }

    /**
     * 打开浏览器,并跳转到指定页面。先判断是否有系统浏览器，有直接打开，没有就弹出选择浏览器框
     *
     * @param context Context
     * @param url     页面链接
     */
    public static void openBrowser(Context context, String url) {
        if (context == null) return;

        Intent intent = new Intent();
        Uri uri = Uri.parse(url);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(uri);
        if (!(context instanceof Activity))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            context.startActivity(Intent.createChooser(intent, "请选择浏览器"));
        }
    }
}
