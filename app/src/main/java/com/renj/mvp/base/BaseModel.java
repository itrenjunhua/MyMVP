package com.renj.mvp.base;

import com.renj.mvp.app.MyApplication;
import com.renj.mvp.retrofit.ApiServer;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-12   12:11
 * <p>
 * 描述：MVP模式中Model基类，提供ApiServer给子类使用
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class BaseModel {
    protected ApiServer mApiServer;

    public BaseModel() {
    }

    public void initApiServer() {
        mApiServer = MyApplication.applicationComponent.getApiServer();
    }
}
