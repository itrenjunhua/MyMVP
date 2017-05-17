package com.renj.mvp.base;

import android.util.Log;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-12   12:11
 * <p>
 * 描述：MVP模式中Presenter基类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class BasePresenter<T extends BaseModel> {
    private final String TAG = "BasePresenter";
    private final String TAG_INFO1 = "获取泛型类型失败";
    private final String TAG_INFO2 = "创建 <T extends BaseModel> model对象失败";
    private Class<T> mClazz;
    protected T mModel;

    public BasePresenter() {
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
                mModel = mClazz.newInstance();
                mModel.initApiServer();
            } catch (InstantiationException e) {
                e.printStackTrace();
                Log.e(TAG, TAG_INFO2, e);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                Log.e(TAG, TAG_INFO2, e);
            }
        } else {
            Log.e(TAG, TAG_INFO1);
        }
    }
}
