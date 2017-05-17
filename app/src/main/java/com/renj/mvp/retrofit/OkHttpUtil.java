package com.renj.mvp.retrofit;

import android.content.Context;

import okhttp3.OkHttpClient;

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
    public static OkHttpClient initOkHttp(Context context) {
        mOkHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        Request request = chain.request();
//                        HttpUrl url = request.url();
//                        Log.i("OkHttpUtil", "-------- " + url.url().getPath());
//                        return chain.proceed(request);
//                    }
//                })
                .build();
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
