package com.renj.mvp.dagger;

import com.renj.mvp.view.MainActivity;

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
 * 描述：注册所有Activity
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
@Module(includes = ActivityModule.class)
public abstract class ActivityBuilder {
    @ContributesAndroidInjector()
    abstract MainActivity bindMainActivity();
}
