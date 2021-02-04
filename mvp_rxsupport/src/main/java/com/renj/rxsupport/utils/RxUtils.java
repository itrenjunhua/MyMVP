package com.renj.rxsupport.utils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2018-07-31   16:08
 * <p>
 * 描述：RxJava工具类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class RxUtils {

    /**
     * RxJava用于切换线程
     *
     * @param <T> 泛型
     * @return {@link ObservableTransformer} 对象
     */
    public static <T> ObservableTransformer<T, T> threadTransformer() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
