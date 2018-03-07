package com.renj.mvp.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.renj.cachelibrary.CacheManageUtils;
import com.renj.mvp.retrofit.RetrofitUtil;
import com.renj.mvp.utils.SPUtils;

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
public class MyApplication extends Application {
    public static ApplicationComponent mApplicationComponent;
    public static Handler mHandler = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();
        initApplication();
    }

    // 初始化Application
    private void initApplication() {
        // 初始化全局的异常处理机制
        MyExceptionHandler.newInstance().initMyExceptionHandler(this);
        // 初始化 Retrofit
        RetrofitUtil.newInstance().initRetrofit(this);
        // 初始化缓存类
        CacheManageUtils.initCacheUtil(this);
        // 初始化SPUtils
        SPUtils.initConfig(new SPUtils.SPConfig.Builder()
                .spName("config_sp")
                .spMode(Context.MODE_PRIVATE)
                .build());

        // 构建 ApplicationComponent
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
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
