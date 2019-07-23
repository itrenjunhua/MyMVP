package com.renj.utils.system;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import com.renj.utils.common.UIUtils;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：itrenjunhua@163.com
 * <p>
 * 创建时间：2019-01-18   10:45
 * <p>
 * 描述：Gps相关工具类<br/><br/>
 * <b>注意：<br/>
 * <b>1.清单文件添加<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * &lt;uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/&gt;<br/>权限</b><br/><br/>
 * 2.需要动态申请<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * {@link android.Manifest.permission#ACCESS_COARSE_LOCATION}<br/>权限</b><br/>
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class GpsUtils {
    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     *
     * @return true 表示开启
     */
    public static final boolean isOpenGps() {
        LocationManager locationManager = (LocationManager) UIUtils.getContext().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
    }

    /**
     * 打开GPS设置界面
     */
    public static void openGpsSettingActivity(@NonNull Context context) {
        //跳转GPS设置界面
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        context.startActivity(intent);
    }
}
