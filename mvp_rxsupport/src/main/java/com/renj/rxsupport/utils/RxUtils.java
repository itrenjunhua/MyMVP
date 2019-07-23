package com.renj.rxsupport.utils;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
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
    private static RxUtils instance = new RxUtils();

    private RxUtils() {
    }

    @org.jetbrains.annotations.Contract(pure = true)
    public static RxUtils newInstance() {
        return instance;
    }

    /**
     * RxJava用于切换线程
     *
     * @param <T> 泛型
     * @return {@link ObservableTransformer} 对象
     */
    public <T> FlowableTransformer<T, T> threadTransformer() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Flowable<T> apply(Flowable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
