package com.renj.mvp.rxjava;

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
 * 描述：RxJava用于切换线程的Transformer
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ThreadTransformer {
    /**
     * 切换 Observable 线程
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> threadChange() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
