package com.renj.common.dagger;

import com.renj.httplibrary.OkHttpUtil;
import com.renj.httplibrary.RetrofitUtil;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-11   17:48
 * <p>
 * 描述：全局的Module
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
@Module
public class BaseApplicationModule {

    @Provides
    public Retrofit provideRetrofit() {
        return RetrofitUtil.newInstance().getRetrofit();
    }

    @Provides
    public OkHttpClient provideOkHttpClient() {
        return OkHttpUtil.getOkHttpClient();
    }
}
