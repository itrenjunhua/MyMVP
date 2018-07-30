package com.renj.mvp.mode.retrofit;

import android.content.Context;

import com.renj.mvp.mode.retrofit.exception.NetworkException;
import com.renj.mvp.utils.AppConfig;
import com.renj.mvp.utils.Logger;
import com.renj.mvp.utils.NetWorkUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-11   18:11
 * <p>
 * 描述：对OKHttp进行初始化的类(自定义配置OKHttp)
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class OkHttpUtil {
    private static OkHttpClient mOkHttpClient;

    private OkHttpUtil() {
    }

    /**
     * 初始化OkHttpClient，可以自定义配置
     *
     * @param context
     */
    public static OkHttpClient initOkHttp(final Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS);

        // 增加网络状态监听拦截器
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                if (NetWorkUtils.isConnected(context)) {
                    return chain.proceed(chain.request());
                } else {
                    throw new NetworkException("网络连接异常!!!");
                }
            }
        });

        // Debug 模式下打印访问网络的地址和提交的参数
        if (AppConfig.IS_DEBUG) {
            builder.addNetworkInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Logger.d(message);
                }
            }).setLevel(HttpLoggingInterceptor.Level.BODY));
        }

        mOkHttpClient = builder.build();
        return mOkHttpClient;
    }

    /**
     * 获取OkHttpClient
     *
     * @return
     */
    @org.jetbrains.annotations.Contract(pure = true)
    public static OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }
}
