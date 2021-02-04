package com.renj.rxsupport.utils;

import io.reactivex.observers.DisposableObserver;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2018-07-31   14:10
 * <p>
 * 描述：自定义的 {@link DisposableObserver} ，减少重写方法的个数
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class CustomSubscriber<T> extends DisposableObserver<T> {

    @Override
    public void onNext(T t) {
    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onComplete() {

    }
}
