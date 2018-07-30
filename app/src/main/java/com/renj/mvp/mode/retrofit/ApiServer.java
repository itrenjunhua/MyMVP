package com.renj.mvp.mode.retrofit;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-11   17:58
 * <p>
 * 描述：Retrofit框架中定义服务器路径的接口
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public interface ApiServer {
    String BASE_URL = "http://weather.51wnl.com/weatherinfo/";

    @GET("{path}")
    Observable<String> getWeather(@Path("path") String text, @QueryMap Map<String,String> queryMap);
}
