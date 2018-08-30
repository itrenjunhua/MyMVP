package com.renj.mvp.base.dagger;

import com.renj.mvp.MainActivity;
import com.renj.mvp.annotation.ScopeActivity;
import com.renj.mvp.app.ApplicationComponent;
import com.renj.mvp.base.view.BaseActivity;
import com.renj.mvp.test.extend.ExtendModule;
import com.renj.mvp.test.extend.ExtendSubComponent;
import com.renj.mvp.test.fragment.FragmentActivity;
import com.renj.mvp.test.network.WeatherActivity;
import com.renj.mvp.test.normal.NormalActivity;

import dagger.Component;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-12   9:55
 * <p>
 * 描述：{@link BaseActivity}的Component<br/>
 * <b>定义常用的类的注入，如果需要扩展，需要另外写Component，也可以使用<code>@Subcomponent</code></b>
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
@ScopeActivity
@Component(modules = BaseActivityModule.class, dependencies = ApplicationComponent.class)
public interface BaseActivityComponent {
    void inject(MainActivity mainActivity);
    void inject(NormalActivity normalActivity);
    void inject(WeatherActivity weatherActivity);
    void inject(FragmentActivity fragmentActivity);

    // 当 Component 使用的是 Subcomponent 注解时调用
    ExtendSubComponent addSub(ExtendModule extendModule);
}
