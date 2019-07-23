package com.renj.utils.check;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：itrenjunhua@163.com
 * <p>
 * 创建时间：2019-07-22   10:07
 * <p>
 * 描述：ANR监测工具类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ANRWatchDog extends Thread {
    public static final int MESSAGE_WATCHDOG_TIME_TICK = 0;
    /**
     * 判定Activity发生了ANR的时间，必须要小于5秒，否则等弹出ANR，可能就被用户立即杀死了。
     */
    public static final int ACTIVITY_ANR_TIMEOUT = 4900;

    private static int lastTimeTick = -1;
    private static int timeTick = 0;

    private Handler watchDogHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            timeTick++;
            timeTick = timeTick % Integer.MAX_VALUE;
        }
    };

    public static void init() {
        new ANRWatchDog().start();
    }

    @Override
    public void run() {
        while (true) {
            watchDogHandler.sendEmptyMessage(MESSAGE_WATCHDOG_TIME_TICK);
            try {
                Thread.sleep(ACTIVITY_ANR_TIMEOUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 如果相等，说明过了ACTIVITY_ANR_TIMEOUT的时间后watchDogHandler仍没有处理消息，已经ANR了
            if (timeTick == lastTimeTick) {
                throw new ANRException();
            } else {
                lastTimeTick = timeTick;
            }
        }
    }

    class ANRException extends RuntimeException {
        public ANRException() {
            super("应用程序无响应，快来改BUG啊！！");
            Thread mainThread = Looper.getMainLooper().getThread();
            setStackTrace(mainThread.getStackTrace());
        }
    }
}
