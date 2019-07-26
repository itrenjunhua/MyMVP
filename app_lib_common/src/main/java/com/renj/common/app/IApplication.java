package com.renj.common.app;

import android.app.Application;

import com.renj.common.dagger.BaseApplicationComponent;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2019-07-25   0:09
 * <p>
 * 描述：Application 接口
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public interface IApplication {
    void initDagger(BaseApplicationComponent baseApplicationComponent);

    void init(Application application);
}
