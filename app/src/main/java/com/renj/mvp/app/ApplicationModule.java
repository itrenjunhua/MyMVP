package com.renj.mvp.app;

import android.os.Looper;

import com.renj.mvp.retrofit.ApiServer;
import com.renj.mvp.retrofit.OkHttpUtil;
import com.renj.mvp.retrofit.RetrofitUtil;

import javax.inject.Singleton;

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
public class ApplicationModule {
    private MyApplication myApplication;

    public ApplicationModule(MyApplication application) {
        this.myApplication = application;
    }

    @Provides
    @Singleton
    public MyApplication provideApplication() {
        return myApplication;
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit() {
        return RetrofitUtil.newInstance().initRetrofit(myApplication);
    }

    @Provides
    @Singleton
    public ApiServer provideApiServer() {
        return RetrofitUtil.newInstance().getApiServer();
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        return OkHttpUtil.getOkHttpClient();
    }

    @Provides
    @Singleton
    public Looper provideMainLooper() {
        return myApplication.getMainLooper();
    }

    @Provides
    @Singleton
    public Thread provideMainThread() {
        return provideMainLooper().getThread();
    }
}
