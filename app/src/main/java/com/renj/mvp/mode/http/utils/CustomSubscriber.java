package com.renj.mvp.mode.http.utils;

import android.support.annotation.NonNull;

import com.renj.mvp.R;
import com.renj.mvp.base.view.IBaseView;
import com.renj.mvp.mode.http.exception.NetworkException;
import com.renj.mvp.utils.Logger;
import com.renj.mvp.utils.ResUtils;
import com.renj.mvp.utils.UIUtils;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2018-07-31   14:10
 * <p>
 * 描述：自定义的 {@link ResourceSubscriber} ，减少重写方法的个数
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public abstract class CustomSubscriber<T> extends ResourceSubscriber<T> {
    private IBaseView mView;

    public CustomSubscriber(@NonNull IBaseView view) {
        this.mView = view;
    }

    @Override
    public void onNext(T t) {
        onResult(t);
        mView.showContentPage(t);
    }

    /**
     * 正确结果回调
     */
    public abstract void onResult(@NonNull T t);

    @Override
    public void onError(Throwable e) {
        if (e instanceof NetworkException) {
            mView.showNetWorkErrorPage();
            UIUtils.showToastSafe(R.string.no_net_word);
            Logger.e("NetWork Exception(网络连接异常) => " + ResUtils.getString(R.string.no_net_word));
        } else {
            mView.showErrorPage(e);
            Logger.e("Exception Info(异常信息) => " + e);
        }
    }

    @Override
    public void onComplete() {

    }
}
