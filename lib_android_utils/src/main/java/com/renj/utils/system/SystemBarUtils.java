package com.renj.utils.system;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2020-03-27   21:43
 * <p>
 * 描述：状态栏和导航栏相关工具类<br/>
 * 当使用了部分效果之后，状态栏将不会占位，导致布局文件和状态栏重复，<br/>
 * 请在布局文件中不要覆盖在状态栏的布局部分的根布局增加 {@code android:fitsSystemWindows="true"} 属性
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class SystemBarUtils {

    /**
     * 修改状态栏为全透明,4,4以上生效
     *
     * @param activity
     */
    public static void transparencyBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 修改状态栏颜色，支持4.4以上版本
     *
     * @param activity
     * @param colorId
     */
    public static void setStatusBarColor(Activity activity, int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(colorId));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 使用SystemBarTint库使4.4版本状态栏变色，需要先将状态栏设置为透明
            transparencyBar(activity);
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(colorId);
        }
    }

    /**
     * 修改状态栏文字颜色，是否黑色文字
     */
    public static void setStatusBarDark(Activity activity, boolean dark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            switch (RomUtils.getLightStatusBarAvailableRomType()) {
                case RomUtils.AvailableRomType.MIUI:
                    setMIUIStatusBarLightMode(activity, dark);
                    break;
                case RomUtils.AvailableRomType.FLYME:
                    setFlymeLightStatusBar(activity, dark);
                    break;
                case RomUtils.AvailableRomType.ANDROID_NATIVE:
                    setAndroidNativeLightStatusBar(activity, dark);
                    break;
            }
        }
    }

    /**
     * 设置状态栏与内容自适应
     */
    public static void setFitsSystemWindows(Activity activity, boolean isFitsSystemWindows) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ViewGroup contentView = activity.findViewById(android.R.id.content);
            if (contentView == null) {
                return;
            }
            if (contentView.getChildCount() > 0) {
                View pageView = contentView.getChildAt(0);
                if (pageView != null) {
                    pageView.setFitsSystemWindows(isFitsSystemWindows);
                }
            }
        }
    }

    /**
     * 小米修改状态栏文字颜色是否为黑色
     */
    private static boolean setMIUIStatusBarLightMode(Activity activity, boolean dark) {
        boolean result = false;
        Window window = activity.getWindow();
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && RomUtils.isMiUIV7OrAbove()) {
                    // 开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                    if (dark) {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    } else {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                    }
                }
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 魅族修改状态栏文字是否为黑色
     */
    private static boolean setFlymeLightStatusBar(Activity activity, boolean dark) {
        boolean result = false;
        if (activity != null) {
            try {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                activity.getWindow().setAttributes(lp);
                result = true;
            } catch (Exception e) {
            }
        }
        return result;
    }

    /**
     * Android原生修改状态栏、导航栏文字是否为黑色
     */
    private static void setAndroidNativeLightStatusBar(Activity activity, boolean dark) {
        View decor = activity.getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    /**
     * 品牌、系统等辅助类
     */
    private static class RomUtils {
        class AvailableRomType {
            public static final int MIUI = 1;
            public static final int FLYME = 2;
            public static final int ANDROID_NATIVE = 3;
            public static final int NA = 4;
        }

        static int getLightStatusBarAvailableRomType() {
            // 开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错
            if (isMiUIV7OrAbove()) {
                return AvailableRomType.ANDROID_NATIVE;
            }

            if (isMiUIV6OrAbove()) {
                return AvailableRomType.MIUI;
            }

            if (isFlymeV4OrAbove()) {
                return AvailableRomType.FLYME;
            }

            if (isAndroidMOrAbove()) {
                return AvailableRomType.ANDROID_NATIVE;
            }

            return AvailableRomType.NA;
        }

        // Flyme V4的displayId格式为 [Flyme OS 4.x.x.xA]
        // Flyme V5的displayId格式为 [Flyme 5.x.x.x beta]
        private static boolean isFlymeV4OrAbove() {
            String displayId = Build.DISPLAY;
            if (!TextUtils.isEmpty(displayId) && displayId.contains("Flyme")) {
                String[] displayIdArray = displayId.split(" ");
                for (String temp : displayIdArray) {
                    //版本号4以上，形如4.x.
                    if (temp.matches("^[4-9]\\.(\\d+\\.)+\\S*")) {
                        return true;
                    }
                }
            }
            return false;
        }

        // Android Api 23以上
        private static boolean isAndroidMOrAbove() {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
        }

        private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";

        private static boolean isMiUIV6OrAbove() {
            try {
                final Properties properties = new Properties();
                properties.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
                String uiCode = properties.getProperty(KEY_MIUI_VERSION_CODE, null);
                if (uiCode != null) {
                    int code = Integer.parseInt(uiCode);
                    return code >= 4;
                } else {
                    return false;
                }
            } catch (final Exception e) {
                return false;
            }
        }

        static boolean isMiUIV7OrAbove() {
            try {
                final Properties properties = new Properties();
                properties.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
                String uiCode = properties.getProperty(KEY_MIUI_VERSION_CODE, null);
                if (uiCode != null) {
                    int code = Integer.parseInt(uiCode);
                    return code >= 5;
                } else {
                    return false;
                }
            } catch (final Exception e) {
                return false;
            }
        }
    }
}
