package com.renj.mvpbase.view;

import com.renj.common.utils.Logger;
import com.renj.mvpbase.presenter.BasePresenter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


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
public abstract class BasePresenterFragment<T extends BasePresenter> extends BaseFragment {
    private final String TAG_INFO = "BasePresenterFragment 创建 T extends BasePresenter 失败 => ";

    private Class<T> mClazz;
    protected T mPresenter;

    @Override
    protected void initPresenter() {
        // 通过反射获取泛型的Class
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            try {
                mClazz = (Class<T>) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
                mPresenter = mClazz.newInstance();
                if (null != mPresenter)
                    mPresenter.attachView(this);
            } catch (Exception e) {
                e.printStackTrace();
                Logger.e(TAG_INFO + e);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mPresenter)
            mPresenter.detachView();
    }
}
