package com.renj.mvp.app;

import com.renj.mvp.retrofit.ApiServer;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-11   17:50
 * <p>
 * 描述：全局的Component
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    MyApplication getApplication();

    Retrofit getRetrofit();

    ApiServer getApiServer();

    OkHttpClient getOkHttpClient();
}
