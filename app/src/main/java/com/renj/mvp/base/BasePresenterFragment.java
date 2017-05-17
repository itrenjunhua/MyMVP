package com.renj.mvp.base;

import android.util.Log;

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
public abstract class BasePresenterFragment<T extends BaseControl.IPresenter> extends BaseFragment{
    private final String TAG = "BasePresenterFragment";
    private final String TAG_INFO1 = "获取泛型类型失败";
    private final String TAG_INFO2 = "创建 <T extends BaseControl.IPresenter> mPresenter对象失败";
    private Class<T> mClazz;
    protected T mPresenter;

    @Override
    protected void initPresenter() {
        createPresenterObject();
        if(null != mPresenter)
            mPresenter.attachView(this);
    }

    private void createPresenterObject() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            try {
                mClazz = (Class<T>) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, TAG_INFO1, e);
            }
        }
        if (null != mClazz) {
            try {
                mPresenter = mClazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
                Log.e(TAG,TAG_INFO2 , e);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                Log.e(TAG, TAG_INFO2, e);
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
                Log.e(TAG, TAG_INFO2, e);
            }
        } else {
            Log.e(TAG, TAG_INFO1);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(null != mPresenter)
            mPresenter.detachView(this);
    }
}
