package com.renj.mvp.base;

import java.util.Map;

import io.reactivex.Observable;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-12   12:23
 * <p>
 * 描述：MVP模式中的接口基类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public interface BaseControl {
    interface IModel<T> {
        Observable<T> getData(String path, Map<String, String> queryMap);

        Observable<T> refreshData(String path, Map<String, String> queryMap);
    }

    interface IView {
        int getLayoutId();

        void initData();
    }

    interface IPresenter<M extends IView> {
        void attachView(M view);

        void detachView(M view);

        void getData(String path, Map<String, String> queryMap);

        void refreshData(String path, Map<String, String> queryMap);
    }

}
