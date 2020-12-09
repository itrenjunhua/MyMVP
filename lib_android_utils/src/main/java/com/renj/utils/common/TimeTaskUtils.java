package com.renj.utils.common;

import java.util.Timer;
import java.util.TimerTask;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2020-09-08   11:13
 * <p>
 * 描述：定时任务工具类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class TimeTaskUtils {
    private Timer timer;
    private TimerTask task;
    private long delay;
    private long time;

    public TimeTaskUtils(long time, TimerTask task) {
        this(time, 0, task);
    }

    public TimeTaskUtils(long time, long delay, TimerTask task) {
        this.task = task;
        this.delay = delay;
        this.time = time;
        if (timer == null) {
            timer = new Timer();
        }
    }

    public void start() {
        timer.schedule(task, delay, time);// 每隔time时间段就执行一次
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
        }
        if (task != null) {
            task.cancel();  //将原任务从队列中移除
        }
    }
}
