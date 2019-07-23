package com.renj.utils.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import com.renj.utils.common.UIUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：itrenjunhua@163.com
 * <p>
 * 创建时间：2018-12-19   19:42
 * <p>
 * 描述：WiFi相关工具类<br/><br/>
 * <b>注意：需要动态申请<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@link android.Manifest.permission#ACCESS_FINE_LOCATION},<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@link android.Manifest.permission#ACCESS_COARSE_LOCATION},<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@link android.Manifest.permission#ACCESS_WIFI_STATE}<br/>权限</b>
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class WiFiUtils {
    private static WiFiUtils instance;
    private static WifiManager wifiManager;

    /**
     * 单例设计，获取 {@link WiFiUtils}
     *
     * @return
     */
    public static WiFiUtils getInstance() {
        if (instance == null) {
            synchronized (WiFiUtils.class) {
                if (instance == null) {
                    instance = new WiFiUtils();
                    wifiManager = (WifiManager) UIUtils.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                }
            }
        }
        return instance;
    }

    /**
     * 获取当前设备连接的WiFi
     *
     * @return 当前设备连接的WiFi名称
     */
    public String getSSid() {
        if (!isConnectionWiFi()) return "";
        if (wifiManager != null) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo != null) {
                String ssid = wifiInfo.getSSID();
                if (ssid.length() > 2 && ssid.startsWith("\"") && ssid.endsWith("\"")) {
                    return ssid.substring(1, ssid.length() - 1);
                } else {
                    return ssid;
                }
            }
        }
        return "";
    }

    /**
     * 是否打开了WiFi
     *
     * @return
     */
    public boolean isWifiEnabled() {
        return wifiManager.isWifiEnabled();
    }

    /**
     * 打开WiFi
     *
     * @return
     */
    public boolean openWiFi() {
        if (!isWifiEnabled()) {
            return wifiManager.setWifiEnabled(true);
        }
        return true;
    }

    /**
     * 是否连接了WiFi
     *
     * @return
     */
    public boolean isConnectionWiFi() {
        WifiManager wifiManager = (WifiManager) UIUtils.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        if (wifiManager.isWifiEnabled() && connectionInfo.getIpAddress() != 0)
            return true;
        return false;
    }

    /**
     * 开始扫描 WiFi
     */
    public void startScanWiFi() {
        if (openWiFi()) {
            wifiManager.startScan();
        }
    }

    /**
     * 创建WiFi扫描通知广播，注册广播之后可以监听WiFi扫描、连接状态的改变
     *
     * @param wiFiScanAdapter
     * @return
     */
    public BroadcastReceiver createBroadcastReceiver(final WiFiScanAdapter wiFiScanAdapter) {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(intent.getAction())) { // 扫描完成
                    List<ScanResult> list = wifiManager.getScanResults();
                    if (wiFiScanAdapter != null)
                        wiFiScanAdapter.onFinishScan(list);
                } else if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) { // 用于判断是否连接到了有效 WiFi（不能用于判断是否能够连接互联网）
                    NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                    WifiInfo wifiInfo = intent.getParcelableExtra(WifiManager.EXTRA_WIFI_INFO);
                    boolean isConnectionWiFi = false;
                    String ssid = "";
                    if (NetworkInfo.State.CONNECTED.equals(networkInfo.getState())) {
                        isConnectionWiFi = true;
                        ssid = wifiInfo.getSSID();
                        if (ssid.length() > 2 && ssid.startsWith("\"") && ssid.endsWith("\"")) {
                            ssid = ssid.substring(1, ssid.length() - 1);
                        }
                    } else if (NetworkInfo.State.DISCONNECTED.equals(networkInfo.getState())) {
                        isConnectionWiFi = false;
                    }
                    if (wiFiScanAdapter != null)
                        wiFiScanAdapter.onConnectionWiFi(isConnectionWiFi, networkInfo, wifiInfo, ssid);
                } else if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                    // 这个监听 WiFi 的打开与关闭，与 WiFi 的连接无关
                    // 它有5中状态，分别是：打开状态，打开中状态，关闭状态，关闭中状态，未知状态
                    int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, -1);
                    if (wiFiScanAdapter != null)
                        wiFiScanAdapter.onWiFiStatusChangeWiFi(wifiState);
                }
            }
        };
    }

    public static abstract class WiFiScanAdapter {
        /**
         * 监听 WiFi 的打开与关闭，与 WiFi 的连接无关
         *
         * @param wifiState WiFi 打开关闭状态 5中状态，分别是：打开状态，打开中状态，关闭状态，关闭中状态，未知状态
         */
        public void onWiFiStatusChangeWiFi(int wifiState) {
        }

        /**
         * 用于判断是否连接到了有效 WiFi（不能用于判断是否能够连接互联网）
         *
         * @param isConnectionWiFi 是否连接上
         * @param networkInfo
         * @param wifiInfo
         */
        public void onConnectionWiFi(boolean isConnectionWiFi, NetworkInfo networkInfo, @Nullable WifiInfo wifiInfo, String ssid) {
        }

        /**
         * 扫描完成
         *
         * @param scanResults 扫描结果
         */
        public void onFinishScan(List<ScanResult> scanResults) {
        }
    }
}
