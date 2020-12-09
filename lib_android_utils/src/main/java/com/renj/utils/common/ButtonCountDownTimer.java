package com.renj.utils.common;

import android.support.annotation.NonNull;
import android.widget.TextView;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2018-12-27   15:43
 * <p>
 * 描述：按钮倒计时器
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ButtonCountDownTimer extends CustomCountDownTimer {
    private TextView textView;
    private String finishMessage;

    /**
     * @param millisInFuture    默认时长
     * @param countDownInterval 间隔时长
     * @param textView          按钮控件
     * @param finishMessage     倒计时完成后显示文案
     */
    public ButtonCountDownTimer(long millisInFuture, long countDownInterval, @NonNull TextView textView, @NonNull String finishMessage) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
        this.finishMessage = finishMessage;
    }

    @Override
    public void startTimer() {
        super.startTimer();
        if (textView != null) {
            textView.setEnabled(false);
        }
    }

    public void cancelTimer(boolean enable, @NonNull String cancelMessage) {
        super.cancelTimer();
        if (textView != null) {
            textView.setEnabled(enable);
            textView.setText(cancelMessage);
        }
    }

    @Override
    public void onFinish() {
        super.onFinish();
        if (textView != null) {
            textView.setEnabled(true);
            textView.setText(finishMessage);
        }
    }
}
