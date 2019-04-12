package com.renj.mvpbase.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.renj.common.utils.ResUtils;
import com.renj.common.utils.UIUtils;
import com.renj.mvpbase.R;
import com.renj.mvpbase.mode.MvpBaseRB;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-11   18:39
 * <p>
 * 描述：Fragment的基类<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * 如果一个新的Fragment不需要访问网络，那么就直接继承{@link BaseFragment}<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * 如果一个新的Fragment需要访问防落，那么可以继承{@link BasePresenterFragment}
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public abstract class BaseFragment extends Fragment implements IBaseView, View.OnClickListener {

    private Unbinder bind;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(getLayoutId(), null);
        bind = ButterKnife.bind(this, view);
        initPresenter();
        initData();
        return view;
    }

    /**
     * 初始化Presenter，在{@link BasePresenterFragment}中重写
     */
    protected void initPresenter() {

    }

    @Override
    public void onClick(View v) {
        handlerClick(v, v.getId());
    }

    /**
     * 处理点击事件时，只需要重写该方法即可，不需要再实现{@link View.OnClickListener}接口
     *
     * @param v   点击的控件
     * @param vId 点击的控件id
     */
    protected void handlerClick(View v, int vId) {

    }

    @Override
    public <E> void showContentPage(@IntRange int requestCode, @NonNull E e) {
        closeLoadingDialog();
    }

    @Override
    public void showLoadingPage(@IntRange int requestCode) {

    }

    @Override
    public <E extends MvpBaseRB> void showEmptyDataPage(@IntRange int requestCode, @NonNull E e) {
        closeLoadingDialog();
    }

    @Override
    public void showNetWorkErrorPage(@IntRange int requestCode) {
        closeLoadingDialog();
    }

    @Override
    public void showErrorPage(@IntRange int requestCode, Throwable e) {
        closeLoadingDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    /****************  加载对话框处理 ***********/

    protected LoadingDialog loadingDialog;

    @Override
    public void showLoadingDialog() {
        loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.setLoadingText(ResUtils.getString(R.string.dialog_default_loading))
                .setSuccessText(ResUtils.getString(R.string.dialog_default_succeed))
                .setFailedText(ResUtils.getString(R.string.dialog_default_fail))
                .setInterceptBack(false)
                .setLoadSpeed(LoadingDialog.Speed.SPEED_TWO);
        loadingDialog.show();
    }

    @Override
    public void showLoadingDialog(@NonNull String loadingMsg) {
        loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.setLoadingText(loadingMsg)
                .setInterceptBack(false)
                .setLoadSpeed(LoadingDialog.Speed.SPEED_TWO);
        loadingDialog.show();
    }

    @Override
    public void showLoadingDialog(@NonNull String loadingMsg, @NonNull String succeedMsg, @NonNull String failMsg) {
        loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.setLoadingText(loadingMsg)
                .setSuccessText(succeedMsg)
                .setFailedText(failMsg)
                .setInterceptBack(false)
                .setLoadSpeed(LoadingDialog.Speed.SPEED_TWO);
        loadingDialog.show();
    }

    @Override
    public void closeLoadingDialog() {
        if (loadingDialog != null)
            loadingDialog.close();
    }

    @Override
    public void closeSucceedDialog() {
        if (loadingDialog != null) {
            loadingDialog.loadSuccess();
            // 绘制完成之后在在关闭进度框
            UIUtils.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadingDialog.close();
                }
            }, 1500);
        }
    }

    @Override
    public void closeFailDialog() {
        if (loadingDialog != null) {
            loadingDialog.loadFailed();
            // 绘制完成之后在在关闭进度框
            UIUtils.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadingDialog.close();
                }
            }, 1500);
        }
    }
}
