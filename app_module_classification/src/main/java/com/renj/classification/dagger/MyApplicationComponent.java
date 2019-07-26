package com.renj.classification.dagger;

import com.renj.common.dagger.BaseApplicationComponent;
import com.renj.common.dagger.BaseApplicationModule;

import dagger.Component;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-04-15   14:05
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
@Component(modules = {BaseApplicationModule.class, MyApplicationModule.class, ActivityBuilder.class, FragmentBuilder.class}, dependencies = {BaseApplicationComponent.class})
public interface MyApplicationComponent {

}
