package com.renj.mvp.retrofit;

import android.content.Context;

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
    private static final RetrofitUtil RETROFIT_UTIL = new RetrofitUtil();
    private static Retrofit mRetrofit;
    private static ApiServer mApiServer;

    private RetrofitUtil() {
    }

    public static RetrofitUtil newInstance() {
        return RETROFIT_UTIL;
    }

    /**
     * 初始化Retrofit
     *
     * @return
     */
    public Retrofit initRetrofit(Context context) {
        if (null == mRetrofit) {
            synchronized (this) {
                if (null == mRetrofit) {
                    mRetrofit = new Retrofit.Builder().
                            baseUrl(Constants.BASE_URL)
                            //增加返回值为String的支持
                            .addConverterFactory(ScalarsConverterFactory.create())
                            //增加返回值为Gson的支持(以实体类返回)
                            .addConverterFactory(GsonConverterFactory.create())
                            //增加返回值为Oservable<T>的支持
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(OkHttpUtil.initOkHttp(context))
                            .build();
                    mApiServer = mRetrofit.create(ApiServer.class);
                }
            }
        }
        return mRetrofit;
    }

    /**
     * 获取ApiServer
     *
     * @return
     */
    public ApiServer getApiServer() {
        return mApiServer;
    }
}
