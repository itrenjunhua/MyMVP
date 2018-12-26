package com.renj.common.utils;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2018-05-22   17:27
 * <p>
 * 描述：数字处理工具类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class NumberUtils {

    /**
     * 将字符串强制转换为 int 数据
     */
    public static int valueOfInteger(String numberString) {
        try {
            return Integer.valueOf(numberString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将字符串强制转换为 double 数据
     */
    public static double valueOfDouble(String numberString) {
        try {
            return Double.valueOf(numberString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0.00;
        }
    }
}
