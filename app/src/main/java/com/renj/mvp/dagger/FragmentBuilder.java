package com.renj.mvp.dagger;

import com.renj.mvp.test.fragment.NormalFragment;
import com.renj.mvp.test.fragment.WeatherFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-04-10   10:44
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
@Module(includes = FragmentModule.class)
public abstract class FragmentBuilder {
    @ContributesAndroidInjector()
    abstract NormalFragment buildNormalFragment();

    @ContributesAndroidInjector()
    abstract WeatherFragment buildWeatherFragment();
}
