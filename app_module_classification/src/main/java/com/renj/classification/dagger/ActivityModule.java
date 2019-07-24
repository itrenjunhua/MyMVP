package com.renj.classification.dagger;

import com.renj.mvp.view.fragment.FoundFragment;
import com.renj.mvp.view.fragment.HomeFragment;
import com.renj.mvp.view.fragment.MyFragment;

import dagger.Module;
import dagger.Provides;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-12   9:55
 * <p>
 * 描述：Activity 中共用的Module {@link ActivityBuilder}
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
@Module
public class ActivityModule {

    @Provides
    public HomeFragment provideHomeFragment() {
        return HomeFragment.Companion.newInstance();
    }

    @Provides
    public FoundFragment provideFoundFragment() {
        return FoundFragment.Companion.newInstance();
    }

    @Provides
    public MyFragment provideMyFragment() {
        return MyFragment.Companion.newInstance();
    }
}
