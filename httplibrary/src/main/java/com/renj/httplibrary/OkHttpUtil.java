package com.renj.httplibrary;

import android.content.Context;

import com.renj.common.CommonUtils;
import com.renj.common.utils.Logger;
import com.renj.common.utils.NetWorkUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    final static List<Interceptor> interceptorList = new ArrayList<>();
    final static List<Interceptor> networkInterceptorList = new ArrayList<>();

    private OkHttpUtil() {
    }

    /**
     * 初始化OkHttpClient，可以自定义配置
     *
     * @param context
     */
    static OkHttpClient initOkHttp(final Context context) {
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

        // 增加拦截器 addInterceptor
        if (interceptorList.size() > 0) {
            for (Interceptor interceptor : interceptorList) {
                builder.addInterceptor(interceptor);
            }
        }

        // 增加网络拦截器 addNetworkInterceptor
        if (networkInterceptorList.size() > 0) {
            for (Interceptor interceptor : networkInterceptorList) {
                builder.addNetworkInterceptor(interceptor);
            }
        }

        // Debug 模式下打印访问网络的地址和提交的参数
        if (CommonUtils.isDebug()) {
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
    public static OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }
}
