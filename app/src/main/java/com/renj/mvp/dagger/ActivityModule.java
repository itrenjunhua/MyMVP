package com.renj.mvp.dagger;

import com.renj.mvp.base.view.BaseActivity;
import com.renj.mvp.test.fragment.NormalFragment;
import com.renj.mvp.test.fragment.WeatherFragment;

import dagger.Module;
import dagger.Provides;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-12   9:55
 * <p>
 * 描述：{@link BaseActivity}的Module，定义常用的类<br/>
 * <b>如果有其他的比较特别的，需要另外扩展Module</b>
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
@Module
public class ActivityModule {
    @Provides
    public NormalFragment provideNormal(){
        return new NormalFragment();
    }

    @Provides
    public WeatherFragment provideWeather(){
        return new WeatherFragment();
    }
}
