package com.renj.mvp.utils.rxjava;

import android.support.annotation.NonNull;

import com.trello.rxlifecycle2.LifecycleProvider;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-12   12:51
 * <p>
 * 描述：RxJava用于切换线程和绑定生命周期的Transformer
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class MyObservableTransformer<T, V extends LifecycleProvider> implements ObservableTransformer<T, T> {
    private V mView;

    public MyObservableTransformer(@NonNull V view) {
        this.mView = view;
    }

    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        return upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mView.<T>bindToLifecycle());
    }
}
