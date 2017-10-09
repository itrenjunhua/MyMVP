package com.renj.mvp.base;

import com.renj.mvp.utils.MyLogger;

import javax.inject.Inject;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-12   10:22
 * <p>
 * 描述：需要访问网络的Activity的基类，同时也是{@link BaseActivity}的子类<br/>
 * 如果定义了泛型参数，那么就会将该泛型的Presenter初始化出来，子类直接使用即可。参数名：<code>mPresenter</code>
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public abstract class BasePresenterActivity<T extends BasePresenter> extends BaseActivity{
    private final String TAG_INFO = "创建 <T extends BaseControl.IPresenter> mPresenter对象失败";

    @Inject
    protected T mPresenter;

    @Override
    protected void initPresenter() {
        if(null != mPresenter)
            mPresenter.attachView(this);
        else
            MyLogger.e(TAG_INFO);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != mPresenter)
            mPresenter.detachView(this);
    }
}
