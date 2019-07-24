package com.renj.mvp.dagger;

import com.renj.httplibrary.OkHttpUtil;
import com.renj.httplibrary.RetrofitUtil;
import com.renj.mvp.mode.db.DBHelper;
import com.renj.mvp.mode.file.FileHelper;
import com.renj.mvp.mode.http.ApiServer;
import com.renj.mvp.mode.http.HttpHelper;

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


    @Provides
    public Retrofit provideRetrofit() {
        return RetrofitUtil.newInstance().getRetrofit();
    }

    @Provides
    public ApiServer provideApiServer() {
        return RetrofitUtil.newInstance().getApiService(ApiServer.class);
    }

    @Provides
    public OkHttpClient provideOkHttpClient() {
        return OkHttpUtil.getOkHttpClient();
    }

    @Provides
    public HttpHelper provideHttpHelper() {
        return new HttpHelper();
    }

    @Provides
    public FileHelper provideFileHelper() {
        return new FileHelper();
    }

    @Provides
    public DBHelper provideDBHelper() {
        return new DBHelper();
    }
}
