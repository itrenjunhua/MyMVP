package com.renj.mvp.rxjava;

import android.support.annotation.NonNull;

import com.renj.mvp.base.IBaseView;
import com.renj.mvp.utils.MyLogger;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-12-12   17:06
 * <p>
 * 描述：自定义的 Observer ，减少重写的方法个数，如果有接口需要单独处理，重写相关方法即可
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public abstract class CustomObserver<T, V extends IBaseView> implements Observer<T> {
    private V mView;

    public CustomObserver(@NonNull V view) {
        this.mView = view;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onError(Throwable e) {
        mView.stateError();
        MyLogger.e("Get Data Error => " + e);
    }

    @Override
    public void onComplete() {

    }
}
