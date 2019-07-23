package com.renj.utils.res;

import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：itrenjunhua@163.com
 * <p>
 * 创建时间：2018-11-12   15:52
 * <p>
 * 描述：操作字符串的工具类，包含对字符串的常用操作
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class StringUtils {
    /**
     * 判断字符串是否为空
     *
     * @param value 需要判断的字符串
     * @return 如果为 "" 、"null"、{@code null} 返回 true，否则 返回 false
     */
    @org.jetbrains.annotations.Contract(value = "null -> true")
    public static boolean isEmpty(String value) {
        if (null == value || "".equals(value) || "null".equals(value))
            return true;
        return false;
    }

    /**
     * 判断多个字符串是否为空
     *
     * @param args 需要判断的字符串
     * @return 如果有一个为空，则返回true，只有全部不为空才返回false
     */
    @org.jetbrains.annotations.Contract("null -> true")
    public static boolean isEmptys(String... args) {
        if (null == args) return true;
        if (0 == args.length) return true;

        for (String arg : args) {
            if (isEmpty(arg)) return true;
        }
        return false;
    }

    /**
     * 判断多个字符串是否相等
     *
     * @param args 需要判断的字符串数组
     * @return 如果其中有一个为空字符串或者null，则返回false，只有全相等才返回true
     */
    @org.jetbrains.annotations.Contract(value = "null -> false")
    public static boolean isEquals(String... args) {
        if (args == null) return false;
        if (args.length <= 1) return false;
        String last = args[0];
        if (isEmpty(last)) return false;
        for (int i = 1; i < args.length; i++) {
            String str = args[i];
            if (isEmpty(str)) return false;
            if (!str.equals(last)) return false;
            last = str;
        }
        return true;
    }

    /**
     * 对 "" 、"null"、{@code null} 进行处理，返回 ""，否则返回 原字符串
     *
     * @param value 需要处理的字符串
     * @return 返回 "" 或者 原字符串
     */
    public static String handlerString(String value) {
        if (isEmpty(value)) return "";
        return value;
    }

    /**
     * 返回一个高亮spannable
     *
     * @param content 全部文本内容
     * @param color   高亮颜色
     * @param start   起始高亮位置
     * @param end     结束高亮位置
     * @return 高亮spannable
     */
    public static CharSequence getHighLightText(String content, int color, int start, int end) {
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        start = start >= 0 ? start : 0;
        end = end <= content.length() ? end : content.length();
        SpannableString spannable = new SpannableString(content);
        CharacterStyle span = new ForegroundColorSpan(color);
        spannable.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    /**
     * 格式化手机或电话号码
     *
     * @param number 号码
     * @param type   0：11位手机号码；1 其他
     * @param symbol 中间分隔符
     * @return
     */
    @NonNull
    public static String formatNumber(@NonNull String number, int type, @NonNull String symbol) {
        if (type == 0) {
            //手机号，在最前面加一个空格
            number = " " + number.trim();
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i < number.length(); i++) {
            sb.append(number.charAt(i));
            if (i % 4 == 0) {
                sb.append(symbol);
            }
        }
        if (number.length() % 4 == 0) {
            String str = sb.toString();
            return str.substring(0, str.length() - 1).trim();
        }
        return sb.toString().trim();// trim掉可能是手机号的空格
    }
}
