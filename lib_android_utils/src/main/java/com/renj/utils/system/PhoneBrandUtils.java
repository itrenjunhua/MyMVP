package com.renj.utils.system;

import android.os.Build;
import android.support.annotation.IntDef;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2020-01-14   15:02
 * <p>
 * 描述：手机品牌工具类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class PhoneBrandUtils {

    @IntDef({
            PhoneBrandValue.HUAWEI,
            PhoneBrandValue.XIAOMI,
            PhoneBrandValue.MEIZU,
            PhoneBrandValue.OPPO,
            PhoneBrandValue.VIVO,
            PhoneBrandValue.OTHER,
    })
    public @interface PhoneBrandValue {
        int HUAWEI = 0;
        int XIAOMI = 1;
        int MEIZU = 2;
        int OPPO = 3;
        int VIVO = 4;
        int OTHER = -1;
    }

    /**
     * 获取手机厂商
     *
     * @return {@link PhoneBrandValue}
     */
    @PhoneBrandValue
    public static int getPhoneBrandValue() {
        String brand = getPhoneBrandName();
        if (brand == null) {
            return PhoneBrandValue.OTHER;
        }
        brand = brand.toUpperCase();
        if ("HUAWEI".equals(brand)) {
            return PhoneBrandValue.HUAWEI;
        }
        if ("XIAOMI".equals(brand)) {
            return PhoneBrandValue.XIAOMI;
        }
        if ("MEIZU".equals(brand)) {
            return PhoneBrandValue.MEIZU;
        }
        if ("OPPO".equals(brand)) {
            return PhoneBrandValue.OPPO;
        }
        if ("VIVO".equals(brand)) {
            return PhoneBrandValue.VIVO;
        }
        return PhoneBrandValue.OTHER;
    }

    /**
     * 获取手机厂商名
     */
    public static String getPhoneBrandName() {
        return Build.BRAND;
    }
}
