package com.renj.httplibrary;

import android.content.Context;
import android.support.annotation.NonNull;

import com.renj.httplibrary.converter.MapConverterFactory;
import com.renj.utils.collection.ListUtils;
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
        if (apiServer != null)
            apiServerList.add(apiServer);
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
     * 初始化Retrofit，如果有多个 baseUrl，可以调用多次该方法，但是需要重新调用 {@link #addApiServerClass(Class)} 增加接口类(ApiServer)
     * ({@link #addApiServerClass(Class)} 方法在 {@link #initRetrofit(String)} 后会被初始化，需要再次调用)。<br/><br/>
     * 注意：1. 接口类(ApiServer)不能重复添加。每个baseUrl都应该有对应的一个或多个接口类(ApiServer)；但是每一个接口类(ApiServer)只能对应一个 baseUrl。<br/>
     * 2. {@link #addInterceptor(Interceptor)} 和 {@link #addNetworkInterceptor(Interceptor)} 两个方法添加的拦截器会共用
     * (不会随着 {@link #initRetrofit(String)} 的调用而重新初始化)<br/><br/>
     *
     * <pre>
     * RetrofitUtil.newInstance()
     *             .addApiServerClass(ApiServer1.class)
     *             .addInterceptor((chain) -> {
     *                Request originalRequest = chain.request();
     *                // 拼接 APP_TOKEN 头
     *                Request sessionIdRequest = originalRequest.newBuilder()
     *                        .addHeader(AppConfig.APP_TOKEN_KEY, AppConfig.APP_TOKEN_VALUE)
     *                        .build();
     *                   return chain.proceed(sessionIdRequest);
     *                })
     *            .initRetrofit(ApiServer.BASE_URL1);
     *
     * // 虽然没有调用 addInterceptor() 方法，但是会共用上面的拦截信息
     * RetrofitUtil.newInstance()
     *             .addApiServerClass(ApiServer2.class)
     *             .initRetrofit(ApiServer.BASE_URL2);
     * </pre>
     *
     * @return
     */
    public void initRetrofit(@NonNull String baseUrl) {
        if (StringUtils.isEmpty(baseUrl))
            throw new NullPointerException("参数 baseUrl 不能为空");
        if (ListUtils.isEmpty(apiServerList))
            throw new IllegalStateException("请先调用 RetrofitUtil#addApiServer() 方法增加至少一个接口类(ApiServer)");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                // 增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                // 增加返回值为Map集合的支持
                .addConverterFactory(MapConverterFactory.create())
                // 增加返回值为Gson的支持(以实体类返回)
                .addConverterFactory(GsonConverterFactory.create())
                // 增加返回值为Observable<T>的支持
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                // 使用自定义的 OkHttpClient
                .client(OkHttpUtil.initOkHttp())
                .build();
        for (Class aClass : apiServerList) {
            if (apiServiceMap.containsKey(aClass))
                throw new IllegalStateException("一个接口类(ApiServer)不能被初始化多次：" + aClass.getName());
            apiServiceMap.put(aClass, retrofit.create(aClass));
        }
        apiServerList.clear();
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
