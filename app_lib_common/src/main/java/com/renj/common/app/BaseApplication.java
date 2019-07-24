package com.renj.common.app;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.renj.cachelibrary.CacheManageUtils;
import com.renj.common.R;
import com.renj.common.utils.ImageLoaderUtils;
import com.renj.httplibrary.RetrofitUtil;
import com.renj.pagestatuscontroller.RPageStatusManager;
import com.renj.pagestatuscontroller.annotation.RPageStatus;
import com.renj.utils.AndroidUtils;
import com.renj.utils.common.SPUtils;
import com.renj.utils.common.UIUtils;

import dagger.android.support.DaggerApplication;
import okhttp3.Request;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-11   16:53
 * <p>
 * 描述：BaseApplication 所有的子module都继承他，然后在自己的module中初始化自己特有的
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public abstract class BaseApplication extends DaggerApplication implements IApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initApplication(this);
    }

    public void initApplication(Application application) {
        AndroidUtils.init(application);
        // 初始化全局的异常处理机制
        // MyExceptionHandler.newInstance().initMyExceptionHandler(application);
        // ANR监测
//        if (AndroidUtils.isDebug()) {
//            ANRWatchDog.init();
//        }

        if (AndroidUtils.isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();                  // 打印日志
            ARouter.openDebug();                // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(application); // 尽可能早，推荐在Application中初始化

        // 初始化 Retrofit
        RetrofitUtil.newInstance()
                .addInterceptor((chain) -> {
                    Request originalRequest = chain.request();
                    // 拼接 APP_TOKEN 头
                    Request sessionIdRequest = originalRequest.newBuilder()
                            .addHeader(AppConfig.APP_TOKEN_KEY, AppConfig.APP_TOKEN_VALUE)
                            .build();
                    return chain.proceed(sessionIdRequest);
                })
                .initRetrofit(application, AppConfig.BASE_URL);

        // 初始化SPUtils
        SPUtils.initConfig(new SPUtils.SPConfig.Builder()
                .spName("config_sp")
                .spMode(Context.MODE_PRIVATE)
                .build());

        // 在子线程中初始化相关库
        initOnNewThread(application);
    }

    private void initOnNewThread(Application application) {
        UIUtils.runOnNewThread(() -> {
            // 初始化图片加载库
            ImageLoaderUtils.init(application);
            // 初始化缓存类
            CacheManageUtils.initCacheUtil(application);
            // 配置全局页面状态控制框架
            RPageStatusManager.getInstance()
                    .addPageStatusView(RPageStatus.LOADING, R.layout.status_view_loading)
                    .addPageStatusView(RPageStatus.EMPTY, R.layout.status_view_empty)
                    .addPageStatusView(RPageStatus.NET_WORK, R.layout.status_view_network, new int[]{R.id.tv_net_work, R.id.tv_reload}, false, null)
                    .addPageStatusView(RPageStatus.ERROR, R.layout.status_view_error, R.id.tv_error, null);
        });
    }
}
