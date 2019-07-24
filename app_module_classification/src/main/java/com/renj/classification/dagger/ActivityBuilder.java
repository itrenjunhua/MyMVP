package com.renj.classification.dagger;

import com.renj.classification.view.activity.ClassificationActivity;
import com.renj.classification.view.activity.ClassificationListActivity;
import com.renj.classification.view.activity.CollectionListActivity;
import com.renj.classification.view.activity.SeeListActivity;

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
    abstract ClassificationActivity bindClassificationActivity();

    @ContributesAndroidInjector()
    abstract ClassificationListActivity bindClassificationListActivity();

    @ContributesAndroidInjector()
    abstract SeeListActivity bindSeeListActivity();

    @ContributesAndroidInjector()
    abstract CollectionListActivity bindCollectionListActivity();
}
