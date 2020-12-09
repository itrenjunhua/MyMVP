package com.renj.utils.net;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.renj.utils.AndroidUtils;
import com.renj.utils.io.IOUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.Enumeration;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2019-12-30   13:57
 * <p>
 * 描述：IP 工具类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class IPUtils {

    /**
     * 移动网络获取网络IP地址 ipv4
     *
     * @return
     */
    public static String getLocalIpV4Address() {
        try {
            Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces();
            if (networkInterfaces == null) return null;

            InetAddress inetAddress;
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = (NetworkInterface) networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    inetAddress = inetAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * wifi下获取本地网络IP地址（局域网地址）
     *
     * @return
     */
    public static String getLocalIPAddress() {
        // 获取wifi服务
        WifiManager wifiManager = (WifiManager) AndroidUtils.getApplication().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        // 判断wifi是否开启
        if (wifiManager.isWifiEnabled()) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            return intIpToStringIp(ipAddress);
        }
        return null;
    }

    /**
     * 获取外网IP
     *
     * @return
     */
    public static String getNetWorkIpAddress() {
        BufferedReader buff = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL("https://api.ipify.org");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(5000);//读取超时
            urlConnection.setConnectTimeout(5000);//连接超时
            urlConnection.setDoInput(true);
            urlConnection.setUseCaches(false);

            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream is = urlConnection.getInputStream();
                buff = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = buff.readLine()) != null) {
                    builder.append(line);
                }

                urlConnection.disconnect();
                return builder.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(buff);
        }
        return null;
    }

    /**
     * int 类型 IP 转换为 String 类型 IP
     *
     * @param intIp
     * @return
     */
    public static String intIpToStringIp(int intIp) {
        return (intIp & 0xFF) + "." +
                ((intIp >> 8) & 0xFF) + "." +
                ((intIp >> 16) & 0xFF) + "." +
                (intIp >> 24 & 0xFF);
    }
}
