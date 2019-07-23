package com.renj.utils.number;

import android.support.annotation.NonNull;

import java.text.DecimalFormat;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：itrenjunhua@163.com
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
     * 将字符串强制转换为 int 数据，已处理 {@link NumberFormatException}
     */
    public static int integerValueOf(String numberString) {
        try {
            return Integer.valueOf(numberString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将字符串强制转换为 double 数据，已处理 {@link NumberFormatException}
     */
    public static double doubleValueOf(String numberString) {
        try {
            return Double.valueOf(numberString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0.00;
        }
    }

    /**
     * 格式化double，保留2为小数 每 3 位加 "," 号分割
     *
     * @param formatNum
     * @return
     */
    public static String formatDoubleComma(@NonNull double formatNum) {
        DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getInstance();
        decimalFormat.applyPattern("#,##0.00");
        //decimalFormat.setGroupingSize(3);
        return decimalFormat.format(formatNum);
    }

    /**
     * 格式化double，保留2为小数，不加 "," 号分割
     *
     * @param formatNum
     * @return
     */
    public static String formatDouble(@NonNull double formatNum) {
        DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getInstance();
        decimalFormat.applyPattern("##0.00");
        //decimalFormat.setGroupingSize(3);
        return decimalFormat.format(formatNum);
    }

    /**
     * 格式化double，不保留小数，每 3 位加 "," 号分割
     *
     * @param formatNum
     * @return
     */
    public static String formatDoubleToIntComma(@NonNull double formatNum) {
        DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getInstance();
        decimalFormat.applyPattern("#,##0");
        //decimalFormat.setGroupingSize(3);
        return decimalFormat.format(formatNum);
    }

    /**
     * 格式化double，不保留小数，不加 "," 号分割
     *
     * @param formatNum
     * @return
     */
    public static String formatDoubleToInt(@NonNull double formatNum) {
        DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getInstance();
        decimalFormat.applyPattern("##0");
        //decimalFormat.setGroupingSize(3);
        return decimalFormat.format(formatNum);
    }
}
