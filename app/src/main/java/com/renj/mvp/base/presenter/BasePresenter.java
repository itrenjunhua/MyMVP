package com.renj.mvp.base.presenter;

import com.renj.mvp.base.view.IBaseView;
import com.renj.mvp.mode.ModelManager;

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

    protected ModelManager mModelManager;
    protected T mView;

    public BasePresenter(ModelManager modelManager) {
        this.mModelManager = modelManager;
    }

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }
}
