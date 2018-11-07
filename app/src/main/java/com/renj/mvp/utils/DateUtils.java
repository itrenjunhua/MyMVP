package com.renj.mvp.utils;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-07-07   10:22
 * <p>
 * 描述：日期、时间相关工具类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class DateUtils {
    private final static int SS = 1000;     // 毫秒
    private final static int MI = SS * 60;  // 分钟
    private final static int HH = MI * 60;  // 小时
    private final static int DD = HH * 24;  // 天
    private final static String[] weeks = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    /**
     * 获取长度为13位的时间戳
     *
     * @return
     */
    public static long currentTimeMillis() {
        String timeMillis = System.currentTimeMillis() + "";
        while (timeMillis.length() < 13) {
            timeMillis = timeMillis + "0";
        }
        return Long.parseLong(timeMillis);
    }

    /**
     * 获取系统当前时间并格式化成 年-月-日 小时:分钟:秒 的格式返回
     *
     * @return 返回 当前时间的 年-月-日 小时:分钟:秒 格式
     */
    @NonNull
    @CheckResult(suggest = "返回结果没有使用过")
    public static String getCurrentDate() {
        long currentTimeMillis = System.currentTimeMillis();
        return formatDateAndTime(currentTimeMillis);
    }

    /**
     * 获取系统当前时间并格式化成指定的的格式返回
     *
     * @param timeTemplate 格式模板
     * @return 返回 当前时间的指定格式
     */
    @NonNull
    @CheckResult(suggest = "返回结果没有使用过")
    public static String getCurrentDate(String timeTemplate) {
        long currentTimeMillis = System.currentTimeMillis();
        return formatDateAndTime(currentTimeMillis, timeTemplate);
    }

    /**
     * 根据传入的时间字符串和偏移天数获取到偏移之后的日期和星期
     *
     * @param time           传入的时间字符串
     * @param paramsTemplate 传入的时间字符串格式模板 如："yyyy-MM-dd HH:mm:ss"
     * @param resultTemplate 返回的日期时间格式模板 如："yyyy-MM-dd HH:mm:ss"
     * @param offset         偏移量 单位：天
     * @return strings[0]：日期时间  strings[1]：星期
     */
    public static String[] getDateAndWeek(@NonNull String time, @NonNull String paramsTemplate, @NonNull String resultTemplate, int offset) {
        return getDateAndWeek(formatToLong(time, paramsTemplate), resultTemplate, offset);
    }

    /**
     * 根据传入的时间戳和偏移天数获取到偏移之后的日期和星期
     *
     * @param millis   传入的时间戳
     * @param template 返回的日期时间格式模板 如："yyyy-MM-dd HH:mm:ss"
     * @param offset   偏移量 单位：天
     * @return strings[0]：日期时间  strings[1]：星期
     */
    public static String[] getDateAndWeek(long millis, @NonNull String template, int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        calendar.add(Calendar.DAY_OF_MONTH, offset);

        String[] result = new String[2];

        long timeInMillis = calendar.getTimeInMillis();
        result[0] = formatDateAndTime(timeInMillis, template);

        int week_index = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        result[1] = weeks[week_index];

        return result;
    }

    /**
     * 时间字符串转为long时间戳
     *
     * @param time     字符串时间,注意:格式要与template定义的一样
     * @param template 要格式化的格式:如time为09:21:12那么template为"HH:mm:ss"
     * @return long long时间戳
     */
    @CheckResult(suggest = "返回结果没有使用过")
    @org.jetbrains.annotations.Contract(pure = true)
    public static long formatToLong(@NonNull String time, @NonNull String template) {
        SimpleDateFormat sdf = new SimpleDateFormat(template, Locale.CHINA);
        try {
            Date d = sdf.parse(time);
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            return c.getTimeInMillis();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 根据时间戳得到: 年-月-日 小时:分钟:秒
     *
     * @param leftTime 时间戳
     * @return 年-月-日 小时:分钟:秒
     */
    @NonNull
    @CheckResult(suggest = "返回结果没有使用过")
    public static String formatDateAndTime(long leftTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return sdf.format(leftTime);
    }

    /**
     * 得到指定格式时间
     *
     * @param leftTime       毫秒值
     * @param resultTemplate 结果格式
     * @return 指定格式的时间
     */
    @NonNull
    @CheckResult(suggest = "返回结果没有使用过")
    @org.jetbrains.annotations.Contract(pure = true)
    public static String formatDateAndTime(long leftTime, @NonNull String resultTemplate) {
        SimpleDateFormat sdf = new SimpleDateFormat(resultTemplate, Locale.CHINA);
        return sdf.format(leftTime);
    }

    /**
     * 得到指定格式时间
     *
     * @param date           {@link Date} 对象
     * @param resultTemplate 结果格式
     * @return 指定格式的时间
     */
    @NonNull
    @CheckResult(suggest = "返回结果没有使用过")
    @org.jetbrains.annotations.Contract(pure = true)
    public static String formatDateAndTime(@NonNull Date date, @NonNull String resultTemplate) {
        SimpleDateFormat sdf = new SimpleDateFormat(resultTemplate, Locale.CHINA);
        return sdf.format(date);
    }

    /**
     * 将指定类型的时间戳转为 "距现在多久之前"的字符串
     *
     * @param time     字符串时间,注意:格式要与template定义的一样
     * @param template 要格式化的格式:如time为09:21:12那么template为"HH:mm:ss"
     * @return "距现在多久之前"的字符串
     */
    @NonNull
    @CheckResult(suggest = "返回结果没有使用过")
    public static String formatStandardDate(@NonNull String time, @NonNull String template) {
        long longTime = formatToLong(time, template);
        return formatStandardDate(longTime);
    }

    /**
     * 将long类型时间戳转为 "距现在多久之前"的字符串
     *
     * @param time 时间戳
     * @return "距现在多久之前"的字符串
     */
    @NonNull
    @CheckResult(suggest = "返回结果没有使用过")
    @org.jetbrains.annotations.Contract(pure = true)
    public static String formatStandardDate(long time) {
        StringBuilder sb = new StringBuilder();

        long currentTime = System.currentTimeMillis() - time;
        long mill = (long) Math.ceil(currentTime / SS);// 秒前
        long minute = (long) Math.ceil(currentTime / MI);// 分钟前
        long hour = (long) Math.ceil(currentTime / HH);// 小时
        long day = (long) Math.ceil(currentTime / DD);// 天前

        if (day - 1 > 0) {
            sb.append(day + "天");
            // return formatDateAndTime(time);
        } else if (hour - 1 > 0) {
            if (hour >= 24) {
                sb.append("1天");
                // return formatDateAndTime(time);
            } else {
                sb.append(hour + "小时");
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                sb.append("1小时");
            } else {
                sb.append(minute + "分钟");
            }
        } else if (mill - 1 > 0) {
            if (mill == 60) {
                sb.append("1分钟");
            } else {
                sb.append(mill + "秒");
            }
        } else {
            sb.append("刚刚");
        }
        if (!sb.toString().equals("刚刚")) {
            sb.append("前");
        }
        return sb.toString();
    }

    /**
     * 将多少秒格式化成 几天几小时几分钟的形式
     *
     * @param ss
     * @return
     */
    public static String formatDuration(long ss) {
        ss = ss * SS; // 将秒变为毫秒
        String duration;
        long minutes = (long) Math.ceil(ss / MI);// 分钟
        long hours = (long) Math.ceil(ss / HH);// 小时
        long days = (long) Math.ceil(ss / DD);// 天

        if (days > 0) {
            long tHours = hours - days * 24;
            long tMinutes = minutes - tHours * 60 - days * 24 * 60;
            duration = days + "天" + tHours + "小时" + tMinutes + "分钟";
        } else if (hours > 0) {
            long tMinutes = minutes - hours * 60;
            duration = hours + "小时" + tMinutes + "分钟";
        } else if (minutes > 0) {
            duration = minutes + "分钟";
        } else {
            duration = "不到1分钟";
        }

        return duration;
    }

    /**
     * 格式转换 比如：2017-01-04 12:35:12 转换为 2017-01-04
     *
     * @param time           “2017-01-04 12:35:12” 开始时间值
     * @param template       “yyyy-MM-dd HH:mm:ss” 开始时间类型
     * @param resultTemplate “yyyy-MM-dd” 结果时间类型
     * @return “2017-01-04”类型的值
     */
    @NonNull
    @CheckResult(suggest = "返回结果没有使用过")
    public static String dateConvert(String time, String template, String resultTemplate) {
        return formatDateAndTime(formatToLong(time, template), resultTemplate);
    }

    /**
     * 获取时间差值
     *
     * @param startTime       开始时间
     * @param startTemplate   开始类型
     * @param currentTime     当前时间
     * @param currentTemplate 当前时间类型
     * @return 返回时间差值 int[0] 时 int[1] 分 int[2] 秒
     */
    @NonNull
    @CheckResult(suggest = "返回结果没有使用过")
    @org.jetbrains.annotations.Contract(pure = true)
    public static int[] getTimeDiff(@NonNull String startTime, @NonNull String startTemplate,
                                    @NonNull String currentTime, @NonNull String currentTemplate) {
        int[] result = new int[3];
        long resultLong = formatToLong(currentTime, currentTemplate) - formatToLong(startTime, startTemplate);
        if (resultLong <= 0) {
            result[0] = 0;
            result[1] = 0;
            result[2] = 0;
        } else {
            result[0] = (int) (resultLong / HH);
            result[1] = (int) ((resultLong - result[0] * HH) / MI);
            result[2] = (int) ((resultLong - result[0] * HH - result[1] * MI) / SS);
        }
        return result;
    }

    /**
     * 在给出的时间戳上对 年、月、日、时、分、秒进行偏移，返回偏移后的时间日期 yyyy-MM-dd HH:mm:ss
     *
     * @param mills 时间戳
     * @param year  偏移年
     * @param month 偏移月
     * @param day   偏移日
     * @param h     偏移时
     * @param m     偏移分
     * @param s     偏移秒
     * @return 偏移后的时间日期 yyyy-MM-dd HH:mm:ss
     */
    public static String timeChange(long mills, int year, int month, int day, int h, int m, int s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mills);

        calendar.set(calendar.get(Calendar.YEAR) + year, calendar.get(Calendar.MONTH) + month, calendar.get(Calendar.DAY_OF_MONTH) + day,
                calendar.get(Calendar.HOUR_OF_DAY) + h, calendar.get(Calendar.MINUTE) + m, calendar.get(Calendar.SECOND) + s);

        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 在给出的时间戳上对 年、月、日、时、分、秒进行偏移，返回偏移后的时间日期时间 时间戳
     *
     * @param mills 时间戳
     * @param year  偏移年
     * @param month 偏移月
     * @param day   偏移日
     * @param h     偏移时
     * @param m     偏移分
     * @param s     偏移秒
     * @return 偏移后的时间日期时间 时间戳
     */
    public static long timeChange2(long mills, int year, int month, int day, int h, int m, int s) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mills);

        calendar.set(calendar.get(Calendar.YEAR) + year, calendar.get(Calendar.MONTH) + month, calendar.get(Calendar.DAY_OF_MONTH) + day,
                calendar.get(Calendar.HOUR_OF_DAY) + h, calendar.get(Calendar.MINUTE) + m, calendar.get(Calendar.SECOND) + s);

        return calendar.getTimeInMillis();
    }
}
