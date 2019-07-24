package com.renj.classification.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import com.alibaba.android.arouter.launcher.ARouter;
import com.renj.cachelibrary.CacheManageUtils;
import com.renj.httplibrary.RetrofitUtil;
import com.renj.mvp.R;
import com.renj.mvp.dagger.ActivityModule;
import com.renj.mvp.dagger.ApplicationComponent;
import com.renj.mvp.dagger.ApplicationModule;
import com.renj.mvp.dagger.DaggerApplicationComponent;
import com.renj.mvp.dagger.FragmentModule;
import com.renj.mvp.mode.db.DBHelper;
import com.renj.mvp.mode.db.bean.DaoMaster;
import com.renj.mvp.mode.db.bean.DaoSession;
import com.renj.mvp.mode.file.FileHelper;
import com.renj.mvp.mode.http.ApiServer;
import com.renj.mvp.mode.http.HttpHelper;
import com.renj.common.utils.ImageLoaderUtils;
import com.renj.mvpbase.mode.ModelManager;
import com.renj.pagestatuscontroller.RPageStatusManager;
import com.renj.pagestatuscontroller.annotation.RPageStatus;
import com.renj.utils.AndroidUtils;
import com.renj.utils.common.SPUtils;
import com.renj.utils.common.UIUtils;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import okhttp3.Request;

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
        AndroidUtils.init(this);
        // 初始化全局的异常处理机制
        // MyExceptionHandler.newInstance().initMyExceptionHandler(this);
        // ANR监测
//        if (AndroidUtils.isDebug()) {
//            ANRWatchDog.init();
//        }

        if (AndroidUtils.isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();                  // 打印日志
            ARouter.openDebug();                // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化

        // 初始化数据库框架
        initGreenDao();

        // 初始化 Retrofit
        RetrofitUtil.newInstance()
                .addApiServerClass(ApiServer.class)
                .addInterceptor((chain) -> {
                    Request originalRequest = chain.request();
                    // 拼接 APP_TOKEN 头
                    Request sessionIdRequest = originalRequest.newBuilder()
                            .addHeader(AppConfig.APP_TOKEN_KEY, AppConfig.APP_TOKEN_VALUE)
                            .build();
                    return chain.proceed(sessionIdRequest);
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

    public static DaoSession daoSession;

    /**
     * 初始化GreenDao,直接在Application中进行初始化操作
     */
    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, AppConfig.DATABASE_NAME);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    private void initOnNewThread() {
        UIUtils.runOnNewThread(() -> {
            // 初始化图片加载库
            ImageLoaderUtils.init(MyApplication.this);
            // 初始化缓存类
            CacheManageUtils.initCacheUtil(MyApplication.this);
            // 配置全局页面状态控制框架
            RPageStatusManager.getInstance()
                    .addPageStatusView(RPageStatus.LOADING, R.layout.status_view_loading)
                    .addPageStatusView(RPageStatus.EMPTY, R.layout.status_view_empty)
                    .addPageStatusView(RPageStatus.NET_WORK, R.layout.status_view_network, new int[]{R.id.tv_net_work, R.id.tv_reload}, false, null)
                    .addPageStatusView(RPageStatus.ERROR, R.layout.status_view_error, R.id.tv_error, null);
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
