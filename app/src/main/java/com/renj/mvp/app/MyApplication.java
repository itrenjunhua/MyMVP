package com.renj.mvp.app;

import android.content.Context;
import android.os.Handler;

import com.renj.cachelibrary.CacheManageUtils;
import com.renj.common.CommonUtils;
import com.renj.common.utils.SPUtils;
import com.renj.common.utils.UIUtils;
import com.renj.httplibrary.RetrofitUtil;
import com.renj.mvp.dagger.ActivityModule;
import com.renj.mvp.dagger.ApplicationComponent;
import com.renj.mvp.dagger.ApplicationModule;
import com.renj.mvp.dagger.DaggerApplicationComponent;
import com.renj.mvp.dagger.FragmentModule;
import com.renj.mvp.mode.db.DBHelper;
import com.renj.mvp.mode.file.FileHelper;
import com.renj.mvp.mode.http.ApiServer;
import com.renj.mvp.mode.http.HttpHelper;
import com.renj.mvp.utils.ImageLoaderUtils;
import com.renj.mvpbase.mode.ModelManager;

import java.io.IOException;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-11   16:53
 * <p>
 * 描述：继承至Application的类，设置默认全局的异常处理，初始化全局的相关变量等操作
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class MyApplication extends DaggerApplication {
    public static Handler mHandler = new Handler();

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        ApplicationComponent applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .activityModule(new ActivityModule())
                .fragmentModule(new FragmentModule())
                .build();
        applicationComponent.inject(this);
        return applicationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initApplication();
    }

    // 初始化Application
    private void initApplication() {
        CommonUtils.init(this);
        // 初始化全局的异常处理机制
        MyExceptionHandler.newInstance().initMyExceptionHandler(this);
        // 初始化 Retrofit
        RetrofitUtil.newInstance()
                .addApiServerClass(ApiServer.class)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        // 拼接 阿凡达数据 APP_KEY 参数
                        String httpUrl = originalRequest.url().toString();
                        if (httpUrl.contains("?")) {
                            httpUrl = httpUrl + "&key=" + AppConfig.APP_KEY;
                        } else {
                            httpUrl = httpUrl + "?key=" + AppConfig.APP_KEY;
                        }
                        Request sessionIdRequest = originalRequest.newBuilder()
                                .url(httpUrl)
                                .build();
                        return chain.proceed(sessionIdRequest);
                    }
                })
                .initRetrofit(this, ApiServer.BASE_URL);
        // 初始化 ModelManager，注意 需要先 初始化 Retrofit
        ModelManager.newInstance()
                .addDBHelper(new DBHelper())
                .addFileHelper(new FileHelper())
                .addHttpHelper(new HttpHelper());
        // 初始化SPUtils
        SPUtils.initConfig(new SPUtils.SPConfig.Builder()
                .spName("config_sp")
                .spMode(Context.MODE_PRIVATE)
                .build());

        // 在子线程中初始化相关库
        initOnNewThread();
    }

    private void initOnNewThread() {
        UIUtils.runOnNewThread(new Runnable() {
            @Override
            public void run() {
                // 初始化图片加载库
                ImageLoaderUtils.init(MyApplication.this);
                // 初始化缓存类
                CacheManageUtils.initCacheUtil(MyApplication.this);
            }
        });
    }

    /**
     * 获取主线程的{@link Handler}
     *
     * @return 主线程Handler
     */
    @org.jetbrains.annotations.Contract(pure = true)
    public static Handler getMainThreadHandler() {
        return mHandler;
    }
}
