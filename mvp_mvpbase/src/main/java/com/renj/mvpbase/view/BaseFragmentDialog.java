package com.renj.mvpbase.view;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;

import com.renj.mvpbase.R;

public abstract class BaseFragmentDialog extends DialogFragment {
    private View mDialogView;
    private AnimationSet mModalInAnim;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModalInAnim = (AnimationSet) AnimationUtils.loadAnimation(getContext(), R.anim.module_view_dialog_modal_in);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(getCanceledOnTouchOutside());
        mDialogView = inflater.inflate(getLayout(), container, false);
        initViewAndData(mDialogView);
        return mDialogView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window == null) {
            return;
        }
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ViewGroup.LayoutParams params = window.getAttributes();
        params.width = getDialogWidth();
        params.height = getDialogHeight();
        window.setAttributes((WindowManager.LayoutParams) params);

        mDialogView.startAnimation(mModalInAnim);
    }

    protected int getDialogWidth() {
        return ViewGroup.LayoutParams.MATCH_PARENT;
    }

    protected int getDialogHeight() {
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    protected boolean getCanceledOnTouchOutside() {
        return false;
    }

    protected abstract int getLayout();

    protected abstract void initViewAndData(View rootView);

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null)
            onDismissListener.onDismiss(this);
    }

    private OnDismissListener onDismissListener;

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    public interface OnDismissListener {
        void onDismiss(BaseFragmentDialog baseFragmentDialog);
    }
}
