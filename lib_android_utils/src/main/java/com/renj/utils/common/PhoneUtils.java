package com.renj.utils.common;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.renj.utils.res.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2020-03-15   18:39
 * <p>
 * 描述：手机号码相关工具类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class PhoneUtils {
    /**
     * 校验是否手机号码 正则表达式："^1[3456789][0-9]{9}$"
     *
     * @param phone 校验字符串
     * @return 是否手机号码
     * @throws PatternSyntaxException 正则表达式匹配异常
     */
    public static boolean isPhone(@Nullable String phone) throws PatternSyntaxException {
        if (StringUtils.isEmpty(phone)) return false;

        String regExp = "^1[3456789][0-9]{9}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    /**
     * 将手机号中中间4位变为 *
     *
     * @param phoneNumber
     * @return
     */
    public static String hidePhone(@NonNull String phoneNumber) {
        return hidePhone(phoneNumber, 3, 4);
    }

    /**
     * 将手机号中的部分位置变为 *
     *
     * @param phoneNumber 手机号
     * @param index       从第几位开始变，位数从 0 开始，注意位数，防止角标越界
     * @param count       需要变为 * 的位数，注意位数，防止角标越界
     * @return
     */
    public static String hidePhone(@NonNull String phoneNumber, int index, int count) {
        if (StringUtils.isEmpty(phoneNumber)) return "";
        if (phoneNumber.length() != 11) return "";

        if (index > 10 || index < 0) index = 3;
        if (count > 11 || count < 0) count = 0;
        if (index + count > 11)
            throw new IllegalArgumentException("index + count 不能大于 11 ，手机位数最大为11位");

        StringBuilder stringBuilder = new StringBuilder(11);
        stringBuilder.append(phoneNumber.substring(0, index));
        for (int i = 0; i < count; i++) {
            stringBuilder.append("*");
        }
        int endIndex = index + count;
        if (endIndex < 11)
            stringBuilder.append(phoneNumber.substring(endIndex));
        return stringBuilder.toString();
    }
}
