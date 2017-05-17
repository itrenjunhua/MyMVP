package com.renj.mvp.test.extend;

import dagger.Subcomponent;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-15   18:29
 * <p>
 * 描述：使用 Subcomponent 注解
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
@Subcomponent(modules = ExtendModule.class)
public interface ExtendSubComponent {
    void inject(ExtentActivity extentActivity);
}
