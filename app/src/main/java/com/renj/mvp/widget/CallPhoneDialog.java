package com.renj.mvp.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Dimension;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.renj.mvp.R;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-07-07   15:39
 * <p>
 * 描述：拨打电话的Dialog
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class CallPhoneDialog extends Dialog implements View.OnClickListener {
    private AnimationSet mModalInAnim;
    private View mDialogView;
    private TextView tvPhone, tvCancle, tvOk;
    private CallPhoneListener callPhoneListener;
    private String phoneNumber, cancleText, okText;
    private int phoneColor, cancleColor, okColor;
    private int btSize, phoneSize;

    public CallPhoneDialog(@NonNull Context context) {
        super(context, R.style.alert_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_call_phone);

        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
        mModalInAnim = (AnimationSet) AnimationUtils.loadAnimation(getContext(), R.anim.dialog_modal_in);

        tvPhone = (TextView) findViewById(R.id.tv_phone);
        tvCancle = (TextView) findViewById(R.id.tv_cancle);
        tvOk = (TextView) findViewById(R.id.tv_ok);

        setPhoneNumber(phoneNumber);
        setPhoneColor(phoneColor);
        setCancelText(cancleText);
        setCancelColor(cancleColor);
        setConfirmText(okText);
        setConfirmColor(okColor);
        setBtSize(btSize);
        setPhoneSize(phoneSize);

        tvCancle.setOnClickListener(this);
        tvOk.setOnClickListener(this);
    }

    /**
     * 设置监听
     *
     * @param callPhoneListener
     * @return
     */
    public CallPhoneDialog setCallPhoneListener(CallPhoneListener callPhoneListener) {
        this.callPhoneListener = callPhoneListener;
        return this;
    }

    /**
     * 设置电话号码
     *
     * @param phoneNumber
     * @return
     */
    public CallPhoneDialog setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        if (null != tvPhone)
            tvPhone.setText(phoneNumber);
        return this;
    }

    /**
     * 设置取消按钮文字
     *
     * @param cancleText
     * @return
     */
    public CallPhoneDialog setCancelText(String cancleText) {
        this.cancleText = cancleText;
        if (null != tvCancle)
            tvCancle.setText(cancleText);
        return this;
    }

    /**
     * 设置确定按钮文字
     *
     * @param okText
     * @return
     */
    public CallPhoneDialog setConfirmText(String okText) {
        this.okText = okText;
        if (null != tvOk)
            tvOk.setText(okText);
        return this;
    }

    /**
     * 设置按钮文字大小；单位 sp
     *
     * @param btSize
     * @return
     */
    public CallPhoneDialog setBtSize(int btSize) {
        this.btSize = btSize;
        if (null != tvOk)
            tvOk.setTextSize(Dimension.SP, btSize);
        if (null != tvCancle)
            tvCancle.setTextSize(Dimension.SP, btSize);
        return this;
    }

    /**
     * 设置电话号码文字大小；单位 sp
     *
     * @param phoneSize
     * @return
     */
    public CallPhoneDialog setPhoneSize(int phoneSize) {
        this.phoneSize = phoneSize;
        if (null != tvPhone)
            tvPhone.setTextSize(Dimension.SP, phoneSize);
        return this;
    }

    /**
     * 设置电话号码文字颜色
     *
     * @param phoneColor
     * @return
     */
    public CallPhoneDialog setPhoneColor(int phoneColor) {
        this.phoneColor = phoneColor;
        if (null != tvPhone && 0 != phoneColor)
            tvPhone.setTextColor(phoneColor);
        return this;
    }

    /**
     * 设置取消文字颜色
     *
     * @param cancleColor
     * @return
     */
    public CallPhoneDialog setCancelColor(int cancleColor) {
        this.cancleColor = cancleColor;
        if (null != tvCancle && 0 != cancleColor)
            tvCancle.setTextColor(cancleColor);
        return this;
    }

    /**
     * 设置确定文字颜色
     *
     * @param okColor
     * @return
     */
    public CallPhoneDialog setConfirmColor(int okColor) {
        this.okColor = okColor;
        if (null != tvOk && 0 != okColor)
            tvOk.setTextColor(okColor);
        return this;
    }

    /**
     * 设置触摸Dialog之外是否消失Dialog
     *
     * @param cancel
     * @return
     */
    public CallPhoneDialog setCanceledOnTouchOut(boolean cancel) {
        this.setCanceledOnTouchOutside(cancel);
        return this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDialogView.startAnimation(mModalInAnim);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancle:
                this.dismiss();
                if (null != callPhoneListener)
                    callPhoneListener.onCancle(this);
                break;
            case R.id.tv_ok:
                this.dismiss();
                if (null != callPhoneListener)
                    callPhoneListener.onConfirm(this);
                break;
        }
    }

    public interface CallPhoneListener {
        /**
         * 确定按钮
         *
         * @param dialog
         */
        void onConfirm(CallPhoneDialog dialog);

        /**
         * 取消按钮
         *
         * @param dialog
         */
        void onCancle(CallPhoneDialog dialog);
    }
}
