package com.renj.mvp.dagger;

import android.os.Looper;

import com.renj.mvp.app.MyApplication;
import com.renj.mvp.mode.db.DBHelper;
import com.renj.mvp.mode.file.FileHelper;
import com.renj.mvp.mode.http.ApiServer;
import com.renj.mvp.mode.http.HttpHelper;
import com.renj.mvp.mode.http.utils.OkHttpUtil;
import com.renj.mvp.mode.http.utils.RetrofitUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<String> provideList() {
        return new ArrayList<String>();
    }

    @Provides
    public Map<String, String> provideMap() {
        return new HashMap<>();
    }

    @Provides
    public MyApplication provideApplication() {
        return myApplication;
    }

    @Provides
    public Retrofit provideRetrofit() {
        return RetrofitUtil.newInstance().initRetrofit(myApplication);
    }

    @Provides
    public ApiServer provideApiServer() {
        return RetrofitUtil.newInstance().getApiServer();
    }

    @Provides
    public OkHttpClient provideOkHttpClient() {
        return OkHttpUtil.getOkHttpClient();
    }

    @Provides
    public HttpHelper provideHttpHelper() {
        return new HttpHelper(provideApiServer());
    }

    @Provides
    public FileHelper provideFileHelper() {
        return new FileHelper();
    }

    @Provides
    public DBHelper provideDBHelper() {
        return new DBHelper();
    }

    @Provides
    public Looper provideMainLooper() {
        return myApplication.getMainLooper();
    }

    @Provides
    public Thread provideMainThread() {
        return provideMainLooper().getThread();
    }
}
