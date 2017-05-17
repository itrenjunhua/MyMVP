package com.renj.mvp.test.extend;

import dagger.Component;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-15   18:29
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
@Component(modules = ExtendModule.class)
public interface ExtendComponent {
    void inject(ExtentActivity extentActivity);
}
