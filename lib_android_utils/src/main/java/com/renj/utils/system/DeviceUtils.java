package com.renj.utils.system;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import com.renj.utils.common.UIUtils;

import java.util.Locale;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2020-01-14   15:02
 * <p>
 * 描述：设备信息
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class DeviceUtils {

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return 语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商，对于只适配国内品牌时，可以查看 {@link PhoneBrandUtils#getPhoneBrandValue()}
     *
     * @return 手机厂商
     * @see PhoneBrandUtils#getPhoneBrandName()
     * @see PhoneBrandUtils#getPhoneBrandValue()
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取IMEI，权限 <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
     *
     * @return
     */
    public static String getImei() {
        TelephonyManager tm = (TelephonyManager) UIUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(UIUtils.getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
        return tm.getDeviceId();
    }
}
