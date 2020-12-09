package com.renj.utils.check;

import com.renj.utils.res.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：itrenjunhua@163.com
 * <p>
 * 创建时间：2018-04-18   15:55
 * <p>
 * 描述：校验工具类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class CheckUtils {

    /**
     * 由数字和字母组成，并且要同时含有数字和字母，且长度要在6-20位之间。 正则表达式："^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$"
     *
     * @param password 校验字符串
     * @return 是否匹配正则式
     * @throws PatternSyntaxException 正则表达式匹配异常
     */
    public static boolean isPassword(String password) throws PatternSyntaxException {
        if (StringUtils.isEmpty(password)) return false;

        String regExp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    /**
     * 对传入的正则表达式和字符串进行匹配，返回匹配结果
     *
     * @param reg    正则表达式
     * @param string 校验字符串
     * @return 是否匹配 true：匹配  false：不匹配
     * @throws PatternSyntaxException 正则表达式匹配异常
     */
    public static boolean reg(String reg, String string) throws PatternSyntaxException {
        if (StringUtils.isEmpty(string)) return false;

        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(string);
        return m.matches();
    }

    /**
     * 检查是否为中文
     *
     * @param str 就需要检查的字符串
     * @return 是否为中文
     */
    public static boolean isChinese(String str) {
        String reg = "[\\u4e00-\\u9fa5]+";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 检查一个对象是否为null
     *
     * @param object
     * @return
     */
    public static boolean isNull(Object object) {
        return null == object;
    }
}
