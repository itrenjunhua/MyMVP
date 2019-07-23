package com.renj.mvpbase.presenter;


import com.renj.mvpbase.view.IBaseView;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-12   12:11
 * <p>
 * 描述：MVP模式中Presenter顶层接口
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public interface IBasePresenter<T extends IBaseView> {
    void attachView(T view);

    void detachView();
}
