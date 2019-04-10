package com.renj.mvp.dagger;

import com.renj.mvp.app.MyApplication;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-11   17:50
 * <p>
 * 描述：全局的Component
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
@Component(modules = {AndroidSupportInjectionModule.class, ApplicationModule.class, ActivityBuilder.class, FragmentBuilder.class})
public interface ApplicationComponent extends AndroidInjector<MyApplication> {
    @Override
    void inject(MyApplication instance);
}
