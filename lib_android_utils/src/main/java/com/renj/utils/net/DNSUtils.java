package com.renj.utils.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import com.renj.utils.AndroidUtils;
import com.renj.utils.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.util.LinkedList;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2019-12-30   11:07
 * <p>
 * 描述：DNS 工具类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class DNSUtils {

    /**
     * 获取wifi下的dns地址
     *
     * @return
     */
    public static String getWifiNetDns() {
        WifiManager wifi = (WifiManager) AndroidUtils.getApplication().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifi != null) {
            DhcpInfo info = wifi.getDhcpInfo();
            return IPUtils.intIpToStringIp(info.dns1) + "; " + IPUtils.intIpToStringIp(info.dns2);
        }
        return null;
    }

    /**
     * 获取本地 dns 地址
     *
     * @return
     */
    public static String getLocalDns() {
        /**
         * 获取dns
         */
        String[] dnsServers = getDnsFromCommand();
        if (dnsServers == null || dnsServers.length == 0) {
            dnsServers = getDnsFromConnectionManager();
        }
        /**
         * 组装
         */
        StringBuffer sb = new StringBuffer();
        if (dnsServers != null) {
            for (int i = 0; i < dnsServers.length; i++) {
                sb.append(dnsServers[i]).append("; ");
            }
        }
        return sb.toString();
    }


    /**
     * 通过 getprop 命令获取
     *
     * @return
     */
    private static String[] getDnsFromCommand() {
        LinkedList<String> dnsServers = new LinkedList<>();
        try {
            Process process = Runtime.getRuntime().exec("getprop");
            InputStream inputStream = process.getInputStream();
            LineNumberReader lnr = new LineNumberReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = lnr.readLine()) != null) {
                int split = line.indexOf("]: [");
                if (split == -1) continue;
                String property = line.substring(1, split);
                String value = line.substring(split + 4, line.length() - 1);
                if (property.endsWith(".dns")
                        || property.endsWith(".dns1")
                        || property.endsWith(".dns2")
                        || property.endsWith(".dns3")
                        || property.endsWith(".dns4")) {
                    InetAddress ip = InetAddress.getByName(value);
                    if (ip == null) continue;
                    value = ip.getHostAddress();
                    if (value == null) continue;
                    if (value.length() == 0) continue;
                    dnsServers.add(value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dnsServers.isEmpty() ? new String[0] : dnsServers.toArray(new String[dnsServers.size()]);
    }

    private static String[] getDnsFromConnectionManager() {
        LinkedList<String> dnsServers = new LinkedList<>();
        if (Build.VERSION.SDK_INT >= 21) {
            ConnectivityManager connectivityManager = (ConnectivityManager) AndroidUtils.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null) {
                    for (Network network : connectivityManager.getAllNetworks()) {
                        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
                        if (networkInfo != null && networkInfo.getType() == activeNetworkInfo.getType()) {
                            LinkProperties lp = connectivityManager.getLinkProperties(network);
                            for (InetAddress addr : lp.getDnsServers()) {
                                dnsServers.add(addr.getHostAddress());
                            }
                        }
                    }
                }
            }
        }
        return dnsServers.isEmpty() ? new String[0] : dnsServers.toArray(new String[dnsServers.size()]);
    }

    /**
     * ping 地址
     *
     * @param count    ping 次数
     * @param hostName 主机地址
     * @return
     */
    public static String ping(int count, String hostName) {
        if (count <= 0) count = 3;
        String strRst = null;
        try {
            String str = "ping -c " + count + " " + hostName;
            Process process = Runtime.getRuntime().exec(str);

            // 等待进程执行完毕
            if (process.waitFor() != 0) {
                // 如果进程运行结果不为0,表示进程是错误退出的
                // 获得进程实例的错误输出
                InputStream streamErr = process.getErrorStream();
                strRst = inputStream2String(streamErr);

                if (strRst.isEmpty()) {
                    InputStream streamIn = process.getInputStream();
                    strRst = inputStream2String(streamIn);
                }
                return strRst;
            }

            InputStream streamIn = process.getInputStream();
            strRst = inputStream2String(streamIn);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return strRst;
    }

    private static String inputStream2String(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        IOUtils.close(inputStream);
        return stringBuilder.toString();
    }
}
