package com.renj.mvp.mode.http.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.renj.httplibrary.NetworkException;
import com.renj.mvp.R;
import com.renj.mvp.mode.http.exception.NullDataException;
import com.renj.mvp.mode.http.exception.ResponseException;
import com.renj.mvp.mode.http.exception.TokenException;
import com.renj.mvpbase.view.IBaseView;
import com.renj.mvpbase.view.LoadingStyle;
import com.renj.rxsupport.utils.CustomSubscriber;
import com.renj.utils.common.Logger;
import com.renj.utils.common.UIUtils;
import com.renj.utils.res.ResUtils;
import com.renj.utils.res.StringUtils;

import io.reactivex.observers.DisposableObserver;
import retrofit2.Response;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2018-07-31   14:10
 * <p>
 * 描述：自定义的 {@link DisposableObserver} ，减少重写方法的个数，用于网络响应处理
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public abstract class ResponseSubscriber<T> extends CustomSubscriber<T> {
    @LoadingStyle
    private int loadingStyle;
    private String loadingText;
    private IBaseView mView;

    public ResponseSubscriber(@LoadingStyle int loadingStyle, @Nullable IBaseView view) {
        this.loadingStyle = loadingStyle;
        this.mView = view;
    }

    public ResponseSubscriber(@LoadingStyle int loadingStyle, String loadingText, @NonNull IBaseView view) {
        this(loadingStyle, view);
        this.loadingText = loadingText;
    }

    @Override
    public void onStart() {
        if (mView == null) return;

        if (loadingStyle == LoadingStyle.LOADING_DIALOG && StringUtils.notEmpty(loadingText)) {
            mView.showLoadingDialog(loadingText);
            return;
        }
        mView.showLoadingPage(loadingStyle);
    }

    @Override
    public void onNext(T t) {
        onResult(t);
        if (mView != null)
            mView.showContentPage(loadingStyle, t);
    }

    /**
     * 正确结果回调
     */
    public abstract void onResult(T t);

    @Override
    public void onError(Throwable e) {
        // 网络连接异常
        if (e instanceof NetworkException) {
            if (mView != null)
                mView.showNetWorkErrorPage(loadingStyle);
            UIUtils.showToast(R.string.no_net_work);
            Logger.e("NetWork Exception(网络连接异常) => " + ResUtils.getString(R.string.no_net_work));
        }
        /**
         * 这里还可以对自定义的异常进行处理，这里的异常主要是在 {@link ResponseTransformer#responseResult(Response)} 方法中抛出来的自定义异常
         */
        else if (e instanceof ResponseException) {
            if (mView != null)
                mView.showEmptyDataPage(loadingStyle, null);
        } else if (e instanceof TokenException) {
            UIUtils.showToast("Token 异常");
        } else if (e instanceof NullDataException) {
            // 如果是自定义的异常，那么可以在这里进行对应的处理
            NullDataException nullDataException = (NullDataException) e;
            if (mView != null)
                mView.showEmptyDataPage(loadingStyle, nullDataException.getBaseResponseBean());
            Logger.v("Show Empty Page(显示空页面) => requestCode: " + "；message: " + "重写了 "
                    + ResponseTransformer.class.getName() + ".onNullDataJudge(T t) throws NullDataException 方法对响应数据进行了 null 判断，并抛出了 "
                    + NullDataException.class.getName() + " 异常，将调用 "
                    + IBaseView.class.getName() + ".showEmptyDataPage(@LoadingStyle int loadingStyle, @NonNull E e) 方法。");
        } else {
            if (mView != null)
                mView.showErrorPage(loadingStyle, e);
            Logger.e("Exception Info(异常信息) => " + e);
        }
    }

    @Override
    public void onComplete() {

    }
}
