package com.renj.utils.common;

import android.os.CountDownTimer;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2020-11-17   15:56
 * <p>
 * 描述：倒计时封装
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class CustomCountDownTimer extends CountDownTimer {
    public CustomCountDownTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    public void startTimer() {
        start();

        if (onCountDownListener != null)
            onCountDownListener.onStart();
    }

    public void cancelTimer() {
        cancel();

        if (onCountDownListener != null)
            onCountDownListener.onCancel();
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (onCountDownListener != null)
            onCountDownListener.onTick(millisUntilFinished);
    }

    @Override
    public void onFinish() {
        if (onCountDownListener != null)
            onCountDownListener.onFinish();
    }

    private OnCountDownListener onCountDownListener;

    public void setOnCountDownListener(OnCountDownListener onCountDownListener) {
        this.onCountDownListener = onCountDownListener;
    }

    /**
     * 各种状态监听
     */
    public interface OnCountDownListener {
        /**
         * 开始倒计时
         */
        void onStart();

        /**
         * 正在倒计时
         *
         * @param millisUntilFinished 剩余时间 毫秒
         */
        void onTick(long millisUntilFinished);

        /**
         * 取消倒计时
         */
        void onCancel();

        /**
         * 完成倒计时
         */
        void onFinish();
    }

    public static class SimpleCountDown implements OnCountDownListener {

        /**
         * {@inheritDoc}
         */
        @Override
        public void onStart() {

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void onTick(long millisUntilFinished) {

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void onCancel() {

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void onFinish() {

        }
    }
}
