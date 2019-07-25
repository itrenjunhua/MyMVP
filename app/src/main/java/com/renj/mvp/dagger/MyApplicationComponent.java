package com.renj.mvp.dagger;

import com.renj.common.dagger.BaseApplicationComponent;
import com.renj.mvp.app.MyApplication;

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
@Component(modules = {AndroidSupportInjectionModule.class, MyApplicationModule.class, ActivityBuilder.class, FragmentBuilder.class}, dependencies = {BaseApplicationComponent.class})
public interface MyApplicationComponent extends AndroidInjector<MyApplication> {
    @Override
    void inject(MyApplication instance);
}
