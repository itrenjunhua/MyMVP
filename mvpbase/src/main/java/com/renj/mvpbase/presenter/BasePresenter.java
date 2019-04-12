package com.renj.mvpbase.presenter;


import com.renj.mvpbase.mode.ModelManager;
import com.renj.mvpbase.view.IBaseView;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-09-29   16:53
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class BasePresenter<T extends IBaseView> implements IBasePresenter<T> {

    protected ModelManager mModelManager = ModelManager.newInstance();
    protected T mView;

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }
}
