package com.renj.mvp.dagger;

import com.renj.mvp.view.activity.ClassificationActivity;
import com.renj.mvp.view.activity.ClassificationListActivity;
import com.renj.mvp.view.activity.CollectionListActivity;
import com.renj.mvp.view.activity.MainActivity;
import com.renj.mvp.view.activity.SeeListActivity;
import com.renj.mvp.view.activity.SplashActivity;
import com.renj.mvp.view.activity.WebViewActivity;

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
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector()
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector()
    abstract WebViewActivity bindWebViewActivity();

    @ContributesAndroidInjector()
    abstract ClassificationActivity bindClassificationActivity();

    @ContributesAndroidInjector()
    abstract ClassificationListActivity bindClassificationListActivity();

    @ContributesAndroidInjector()
    abstract SeeListActivity bindSeeListActivity();

    @ContributesAndroidInjector()
    abstract CollectionListActivity bindCollectionListActivity();
}
