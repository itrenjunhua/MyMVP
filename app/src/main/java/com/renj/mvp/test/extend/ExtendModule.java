package com.renj.mvp.test.extend;

import dagger.Module;
import dagger.Provides;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-15   18:25
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
@Module
public class ExtendModule {
    @Provides
    public Preson providePreson(){
        return new Preson();
    }
}
