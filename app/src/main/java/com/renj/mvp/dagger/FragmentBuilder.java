package com.renj.mvp.dagger;

import com.renj.mvp.view.fragment.MyCSDNFragment;
import com.renj.mvp.view.fragment.MyFragment;
import com.renj.mvp.view.fragment.FoundFragment;

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
 * 描述：注册所有Fragment
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
@Module(includes = FragmentModule.class)
public abstract class FragmentBuilder {
    @ContributesAndroidInjector()
    abstract MyCSDNFragment bindMyCSDNFragment();

    @ContributesAndroidInjector()
    abstract FoundFragment bindFoundFragment();

    @ContributesAndroidInjector()
    abstract MyFragment bindMyFragment();
}
