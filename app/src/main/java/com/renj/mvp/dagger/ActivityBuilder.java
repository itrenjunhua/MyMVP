package com.renj.mvp.dagger;

import com.renj.mvp.MainActivity;
import com.renj.mvp.test.extend.ExtendModule;
import com.renj.mvp.test.extend.ExtentActivity;
import com.renj.mvp.test.fragment.FragmentActivity;
import com.renj.mvp.test.network.WeatherActivity;
import com.renj.mvp.test.normal.NormalActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-04-10   10:43
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
@Module(includes = ActivityModule.class)
public abstract class ActivityBuilder {
    @ContributesAndroidInjector()
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector()
    abstract NormalActivity bindNormalActivity();

    @ContributesAndroidInjector()
    abstract WeatherActivity bindWeatherActivity();

    @ContributesAndroidInjector()
    abstract FragmentActivity bindFragmentActivity();

    @ContributesAndroidInjector(modules = ExtendModule.class)
    abstract ExtentActivity bindExtentActivity();
}
