package com.renj.utils.date;

import java.util.Calendar;
import java.util.Objects;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2020-12-10   10:19
 * <p>
 * 描述：日历相关方法工具类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class CalendarUtils {
    /**
     * 获取当前年份
     *
     * @return
     */
    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 获取当前月份 1-12
     *
     * @return
     */
    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前日期是该月的第几天
     *
     * @return
     */
    public static int getCurrentDayOfMonth() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前日期是该周的第几天
     *
     * @return
     */
    public static int getCurrentDayOfWeek() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取指定日期是该周的第几天
     *
     * @return
     */
    public static int getDayOfWeek(int year, int month, int day) {
        Calendar instance = Calendar.getInstance();
        instance.set(year, month, day);
        return instance.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 判断某一个日期是否为今天
     *
     * @return
     */
    public static boolean isToday(int year, int month, int day) {
        return year == getYear() && month == getMonth() && day == getCurrentDayOfMonth();
    }

    /**
     * 获取当月的年份与月份
     *
     * @return int[0]：年份   int[1]：月份 1-12
     */
    public static int[] getCurrentMonth() {
        int[] result = new int[2];
        result[0] = getYear();
        result[1] = getMonth();
        return result;
    }

    /**
     * 获取上一月的年份与月份
     *
     * @param year  年份
     * @param month 月份 1-12
     * @return int[0]：年份   int[1]：月份 1-12
     */
    public static int[] getLastMonth(int year, int month) {
        int[] result = new int[2];
        if (month == 1) {
            result[0] = year - 1;
            result[1] = 12;
        } else {
            result[0] = year;
            result[1] = month - 1;
        }
        return result;
    }

    /**
     * 获取下一月的年份与月份
     *
     * @param year  年份
     * @param month 月份 1-12
     * @return int[0]：年份   int[1]：月份 1-12
     */
    public static int[] getNextMonth(int year, int month) {
        int[] result = new int[2];
        if (month == 12) {
            result[0] = year + 1;
            result[1] = 1;
        } else {
            result[0] = year;
            result[1] = month + 1;
        }
        return result;
    }

    /**
     * 根据传入的年份和月份，获取当前月份的日历分布(<b>注意：一周按从星期天开始</b>)
     *
     * @param year
     * @param month 月份 1-12
     * @return
     */
    public static CalendarInfoBean[][] getDayOfMonthFormat(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);//设置时间为每月的第一天
        //日历格式数组,6行7列
        CalendarInfoBean days[][] = new CalendarInfoBean[6][7];
        //该月的第一天是周几
        int daysOfFirstWeek = calendar.get(Calendar.DAY_OF_WEEK);
        //本月有多少天
        int daysOfMonth = getDaysOfMonth(year, month);
        //上个月有多少天
        int daysOfLastMonth = getLastDaysOfMonth(year, month);
        // 获取上月信息
        int[] lastMonth = getLastMonth(year, month);
        // 获取下月信息
        int[] nextMonth = getNextMonth(year, month);

        int dayNum = 1;
        int nextDayNum = 1;
        //将日期格式填充数组
        for (int i = 0; i < days.length; i++) {
            for (int j = 0; j < days[i].length; j++) {
                if (i == 0 && j < daysOfFirstWeek - 1) {
                    // 上月
                    // 如果该月的第一天是周五，上个月共30天 那么就是 30-(5-1)+1+j，
                    // (5-1)：前面只有4个空位 +1：最后一个位置是30，要占位
                    CalendarInfoBean calendarInfoBean = new CalendarInfoBean(lastMonth[0], lastMonth[1], daysOfLastMonth - daysOfFirstWeek + 2 + j, -1);
                    days[i][j] = calendarInfoBean;
                } else if (dayNum <= daysOfMonth) {
                    // 本月
                    CalendarInfoBean calendarInfoBean = new CalendarInfoBean(year, month, dayNum++, 0);
                    days[i][j] = calendarInfoBean;
                } else {
                    // 下月
                    CalendarInfoBean calendarInfoBean = new CalendarInfoBean(nextMonth[0], nextMonth[1], nextDayNum++, 1);
                    days[i][j] = calendarInfoBean;
                }
            }
        }
        return days;
    }

    /**
     * 根据传入的年份和月份，判断上一个有多少天
     *
     * @param year
     * @param month 月份 1-12
     * @return
     */
    public static int getLastDaysOfMonth(int year, int month) {
        int lastDaysOfMonth;
        if (month == 1) {
            lastDaysOfMonth = getDaysOfMonth(year - 1, 12);
        } else {
            lastDaysOfMonth = getDaysOfMonth(year, month - 1);
        }
        return lastDaysOfMonth;
    }

    /**
     * 根据传入的年份和月份，判断当前月有多少天
     *
     * @param year
     * @param month 月份 1-12
     * @return
     */
    public static int getDaysOfMonth(int year, int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 2:
                if (isLeap(year)) {
                    return 29;
                } else {
                    return 28;
                }
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
        }
        return -1;
    }

    /**
     * 判断是否为闰年
     *
     * @param year
     * @return
     */
    public static boolean isLeap(int year) {
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            return true;
        }
        return false;
    }

    public static class CalendarInfoBean {
        public int year;
        public int monty;
        public int day;
        private int currentMonth; // -1：上月  0：当月 1：下月

        /**
         * 构造函数
         *
         * @param year         年
         * @param monty        月
         * @param day          日
         * @param currentMonth 是否当月  -1：上月  0：当月 1：下月
         */
        public CalendarInfoBean(int year, int monty, int day, int currentMonth) {
            this.year = year;
            this.monty = monty;
            this.day = day;
            this.currentMonth = currentMonth;
        }

        /**
         * 返回星期
         *
         * @return 星期  1：周日 2：周一 ... 6：周五  7：周六
         */
        public int getWeek() {
            return getDayOfWeek(year, monty, day);
        }

        /**
         * 返回是否当月
         *
         * @return -1：上月  0：当月 1：下月
         */
        public int getCurrentMonth() {
            return currentMonth;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CalendarInfoBean calendarInfoBean = (CalendarInfoBean) o;
            return year == calendarInfoBean.year &&
                    monty == calendarInfoBean.monty &&
                    day == calendarInfoBean.day;
        }

        @Override
        public int hashCode() {
            return Objects.hash(year, monty, day);
        }
    }
}
