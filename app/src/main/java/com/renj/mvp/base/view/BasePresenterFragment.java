package com.renj.mvp.base.view;

import com.renj.mvp.base.presenter.BasePresenter;
import com.renj.mvp.utils.Logger;

import javax.inject.Inject;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-12   10:22
 * <p>
 * 描述：需要访问网络的Fragment的基类，同时也是{@link BaseFragment}的子类<br/>
 * 如果定义了泛型参数，那么就会将该泛型的Presenter初始化出来，子类直接使用即可。参数名：<code>mPresenter</code>
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public abstract class BasePresenterFragment<T extends BasePresenter> extends BaseFragment{
    private final String TAG_INFO = "创建 <T extends BaseControl.IPresenter> mPresenter对象失败";

    @Inject
    protected T mPresenter;

    @Override
    protected void initPresenter() {
        if(null != mPresenter)
            mPresenter.attachView(this);
        else
            Logger.e(TAG_INFO);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(null != mPresenter)
            mPresenter.detachView();
    }
}