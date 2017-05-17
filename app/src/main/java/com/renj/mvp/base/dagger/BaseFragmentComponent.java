package com.renj.mvp.base.dagger;

import com.renj.mvp.annotation.ScopFragment;
import com.renj.mvp.app.ApplicationComponent;
import com.renj.mvp.test.fragment.NormalFragment;
import com.renj.mvp.test.fragment.WeatherFragment;

import dagger.Component;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-12   9:55
 * <p>
 * 描述：{@link com.renj.mvp.base.BaseFragment}的Component<br/>
 * <b>定义常用的类的注入，如果需要扩展，需要另外写Component，也可以使用<code>@Subcomponent</code></b>
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
@ScopFragment
@Component(modules = BaseFragmentModule.class, dependencies = ApplicationComponent.class)
public interface BaseFragmentComponent {
    void inject(NormalFragment normalFragment);
    void inject(WeatherFragment weatherFragment);
}
