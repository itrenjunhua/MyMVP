package com.renj.httplibrary;

import android.content.Context;
import android.support.annotation.NonNull;

import com.renj.httplibrary.converter.MapConverterFactory;
import com.renj.utils.res.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-11   18:08
 * <p>
 * 描述：Retrofit工具类，封装Retrofit相关方法
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class RetrofitUtil {
    private volatile static RetrofitUtil RETROFIT_UTIL = new RetrofitUtil();
    private static Retrofit mRetrofit;
    private static List<Class> apiServerList = new ArrayList<>();
    private static Map<Class, Object> apiServiceMap = new HashMap<>();

    private RetrofitUtil() {
    }

    public static RetrofitUtil newInstance() {
        return RETROFIT_UTIL;
    }

    /**
     * 增加ApiServer
     *
     * @param apiServer
     * @return
     */
    public RetrofitUtil addApiServerClass(@NonNull Class apiServer) {
        if (apiServer != null) {
            if (!apiServerList.contains(apiServer)) {
                if (mRetrofit != null) {
                    apiServiceMap.put(apiServer, mRetrofit.create(apiServer));
                } else {
                    apiServerList.add(apiServer);
                }
            }
        }
        return RETROFIT_UTIL;
    }

    /**
     * 增加拦截器
     *
     * @param interceptor
     * @return
     */
    public RetrofitUtil addInterceptor(@NonNull Interceptor interceptor) {
        if (interceptor != null)
            OkHttpUtil.interceptorList.add(interceptor);
        return RETROFIT_UTIL;
    }

    /**
     * 增加网络拦截器
     *
     * @param interceptor
     * @return
     */
    public RetrofitUtil addNetworkInterceptor(@NonNull Interceptor interceptor) {
        if (interceptor != null)
            OkHttpUtil.networkInterceptorList.add(interceptor);
        return RETROFIT_UTIL;
    }

    /**
     * 初始化Retrofit
     *
     * @return
     */
    public Retrofit initRetrofit(Context context, @NonNull String baseUrl) {
        if (StringUtils.isEmpty(baseUrl))
            throw new NullPointerException("参数 baseUrl 不能为空");
        return initRetrofit(context, baseUrl, apiServerList);
    }

    private Retrofit initRetrofit(Context context, final String baseUrl, @NonNull List<Class> classList) {
        if (null == mRetrofit) {
            synchronized (this) {
                if (null == mRetrofit) {
                    mRetrofit = new Retrofit.Builder().
                            baseUrl(baseUrl)
                            // 增加返回值为String的支持
                            .addConverterFactory(ScalarsConverterFactory.create())
                            // 增加返回值为Map集合的支持
                            .addConverterFactory(MapConverterFactory.create())
                            // 增加返回值为Gson的支持(以实体类返回)
                            .addConverterFactory(GsonConverterFactory.create())
                            // 增加返回值为Observable<T>的支持
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            // 使用自定义的 OkHttpClient
                            .client(OkHttpUtil.initOkHttp(context))
                            .build();

                    for (Class aClass : classList) {
                        apiServiceMap.put(aClass, mRetrofit.create(aClass));
                    }
                }
            }
        }
        return mRetrofit;
    }

    public Retrofit getRetrofit() {
        if (mRetrofit == null)
            throw new NullPointerException("请先调用 RetrofitUtil#initRetrofit() 初始化");
        return mRetrofit;
    }

    /**
     * 获取指定的 ApiServer，该 ApiServer 必须是通过 {@link #addApiServerClass(Class)} 方法初始化的，否则返回 {@code null}
     *
     * @param apiServerClass 必须是通过 {@link #addApiServerClass(Class)} 方法初始化的
     * @param <T>
     * @return
     */
    public <T> T getApiService(Class<T> apiServerClass) {
        return (T) apiServiceMap.get(apiServerClass);
    }
}
