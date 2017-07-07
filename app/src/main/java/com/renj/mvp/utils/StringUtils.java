package com.renj.mvp.utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-07-06   18:15
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
     * @return 如果为null或者是空字符串或者只有空格或者为"null"字符串，返回true，否则返回false
     */
    public static boolean isEmpty(String value) {
        if (null != value && !"".equalsIgnoreCase(value.trim()) && !"null".equalsIgnoreCase(value.trim()))
            return false;
        else return true;
    }

    /**
     * 判断多个字符串是否为空
     *
     * @param args 需要判断的字符串
     * @return 如果有一个为空，则返回true，只有全部不为空才返回false
     */
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
     * @param agrs 需要判断的字符串数组
     * @return 如果其中有一个为空字符串或者null，则返回false，只有全相等才返回true
     */
    public static boolean isEquals(String... agrs) {
        String last = null;
        for (int i = 0; i < agrs.length; i++) {
            String str = agrs[i];
            if (isEmpty(str)) return false;
            if (null != last && !str.equalsIgnoreCase(last)) return false;
            last = str;
        }
        return true;
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
}
