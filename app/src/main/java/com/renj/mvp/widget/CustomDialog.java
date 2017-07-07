package com.renj.mvp.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
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
 * 创建时间：2017-07-07   16:20
 * <p>
 * 描述：自定义对话框，可以设置是否显示标题、设置标题内容、主要信息、确认、取消等文字信息和监听，默认不显示标题
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class CustomDialog extends Dialog implements View.OnClickListener {
    private AnimationSet mModalInAnim;
    private View mDialogView;
    private TextView dialogContent, cancle, ok, dialogTitle;
    private CustomDialogListener customDialogListener;
    private String confirmText, cancleText, dialogContentText, title;
    private boolean showTitle = false;

    public CustomDialog(Context context) {
        super(context, R.style.alert_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custom);

        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
        mModalInAnim = (AnimationSet) AnimationUtils.loadAnimation(getContext(), R.anim.dialog_modal_in);

        dialogTitle = (TextView) findViewById(R.id.tv_title_dialog);
        dialogContent = (TextView) findViewById(R.id.tv_dailog_content);
        cancle = (TextView) findViewById(R.id.tv_dialog_cancle);
        ok = (TextView) findViewById(R.id.tv_dialog_ok);

        setTitleContent(title);
        isShowTitle(showTitle);
        setDialogContent(dialogContentText);
        setCancleText(cancleText);
        setConfirmText(confirmText);

        cancle.setOnClickListener(this);
        ok.setOnClickListener(this);
    }

    /**
     * 是否需要显示标题
     *
     * @param showTitle
     * @return
     */
    public CustomDialog isShowTitle(boolean showTitle) {
        this.showTitle = showTitle;
        if (dialogTitle != null) {
            if (this.showTitle) {
                dialogTitle.setVisibility(View.VISIBLE);
            } else {
                dialogTitle.setVisibility(View.GONE);
            }
        }
        return this;
    }

    /**
     * 设置对话框标题
     *
     * @param title
     * @return
     */
    public CustomDialog setTitleContent(String title) {
        this.title = title;
        if (dialogTitle != null) {
            dialogTitle.setText(title);
        }
        return this;
    }

    /**
     * 设置对话框内容
     *
     * @param content
     * @return
     */
    public CustomDialog setDialogContent(String content) {
        this.dialogContentText = content;
        if (dialogContent != null) {
            dialogContent.setText(content);
        }
        return this;
    }

    /**
     * 设置取消按钮内容
     *
     * @param cancleText
     * @return
     */
    public CustomDialog setCancleText(String cancleText) {
        this.cancleText = cancleText;
        if (cancle != null) cancle.setText(cancleText);
        return this;
    }

    /**
     * 设置确定按钮内容
     *
     * @param confirmText
     * @return
     */
    public CustomDialog setConfirmText(String confirmText) {
        this.confirmText = confirmText;
        if (ok != null) ok.setText(confirmText);
        return this;
    }

    /**
     * 设置触摸Dialog之外是否消失Dialog
     *
     * @param cancel
     * @return
     */
    public CustomDialog setCanceledOnTouchOut(boolean cancel) {
        this.setCanceledOnTouchOutside(cancel);
        return this;
    }

    /**
     * 设置监听
     *
     * @param customDialogListener
     * @return
     */
    public CustomDialog setCustomDialogListener(CustomDialogListener customDialogListener) {
        this.customDialogListener = customDialogListener;
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
            case R.id.tv_dialog_cancle:
                this.dismiss();
                if (customDialogListener != null) {
                    customDialogListener.onCancle(this);
                }
                break;
            case R.id.tv_dialog_ok:
                this.dismiss();
                if (customDialogListener != null) {
                    customDialogListener.onConfirm(this);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 按钮监听
     */
    public interface CustomDialogListener {
        /**
         * 点击确定按钮
         *
         * @param dialog
         */
        void onConfirm(CustomDialog dialog);

        /**
         * 点击取消按钮
         *
         * @param dialog
         */
        void onCancle(CustomDialog dialog);
    }
}
