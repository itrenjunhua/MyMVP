package com.renj.common.dagger;

import com.renj.common.app.BaseApplication;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-04-15   14:05
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
@Component(modules = {AndroidSupportInjectionModule.class, BaseApplicationModule.class})
public interface BaseApplicationComponent extends AndroidInjector<BaseApplication> {
    @Override
    void inject(BaseApplication instance);
}
