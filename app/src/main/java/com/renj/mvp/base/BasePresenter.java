package com.renj.mvp.base;

import com.renj.mvp.mode.retrofit.ApiServer;

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

    protected ApiServer mApiServer;
    protected T mView;

    public BasePresenter(ApiServer apiServer) {
        this.mApiServer = apiServer;
    }

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView(T view) {
        this.mView = null;
    }
}
