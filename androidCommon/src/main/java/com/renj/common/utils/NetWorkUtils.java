package com.renj.common.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
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
     * 判断是否连接到网络了
     *
     * @param context
     * @return true：已经连接到网络 false;未连接到网络
     */
    public static boolean isConnected(@NonNull Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
