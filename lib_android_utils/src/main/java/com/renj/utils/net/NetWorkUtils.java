package com.renj.utils.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.IntDef;

import com.renj.utils.AndroidUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：itrenjunhua@163.com
 * <p>
 * 创建时间：2018-04-02   11:30
 * <p>
 * 描述：网路工具类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class NetWorkUtils {
    /**
     * 网络类型
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            ConnectionType.NO_CONNECTION,
            ConnectionType.MOBILE,
            ConnectionType.WIFI,
    })
    public @interface ConnectionType {
        int NO_CONNECTION = -1;
        int MOBILE = 0;
        int WIFI = 1;
    }

    /**
     * 判断是否连接到网络了
     *
     * @return true：已经连接到网络 false;未连接到网络
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) AndroidUtils.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    /**
     * 获取网络类型
     *
     * @return {@link ConnectionType}
     */
    public static int getNetworkType() {
        ConnectivityManager connMgr = (ConnectivityManager) AndroidUtils.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        int networkType = ConnectionType.NO_CONNECTION;
        if (networkInfo != null) {
            int type = networkInfo.getType();
            networkType = type == ConnectivityManager.TYPE_WIFI ? ConnectionType.WIFI : ConnectionType.MOBILE;

        }
        return networkType;
    }

    /**
     * 获取本机地址
     *
     * @return
     */
    public static String getIpAddress() {
        int netType = getNetworkType();
        if (netType == ConnectionType.MOBILE)
            return IPUtils.getLocalIpV4Address();
        if (netType == ConnectionType.WIFI)
            return IPUtils.getLocalIPAddress();
        return "";
    }

    /**
     * 获取外网地址
     *
     * @return
     */
    public static String getNetWorkIpAddress() {
        return IPUtils.getNetWorkIpAddress();
    }

    /**
     * 获取手机DNS
     *
     * @return
     */
    public static String getDns() {
        int netType = getNetworkType();
        if (netType == ConnectionType.MOBILE)
            return DNSUtils.getLocalDns();
        if (netType == ConnectionType.WIFI)
            return DNSUtils.getWifiNetDns();
        return "";
    }

    /**
     * ping 地址
     *
     * @param count    次数
     * @param hostName 主机
     * @return
     */
    public static String ping(int count, String hostName) {
        return DNSUtils.ping(count, hostName);
    }

    /**
     * 流量/手机数据 是否打开
     *
     * @return
     */
    public static boolean isMobileDataEnable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) AndroidUtils.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isMobileDataEnable;
        isMobileDataEnable = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        return isMobileDataEnable;
    }

    /**
     * 是否连接WiFi
     *
     * @return
     */
    public static boolean isWiFi() {
        if (getNetworkType() == ConnectionType.WIFI) {
            return true;
        }
        return false;
    }
}
