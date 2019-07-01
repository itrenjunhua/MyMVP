package com.renj.mvp.mode.http;

import com.renj.mvp.mode.bean.HomeListRPB;
import com.renj.mvp.mode.bean.NewsListRPB;

import io.reactivex.Flowable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

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
    String BASE_URL = "http://129.28.203.98:8888/";

    /**
     * 首页数据
     */
    @GET("home/data")
    Flowable<Response<HomeListRPB>> homeListRequest();

    /**
     * 检索新闻数据
     */
    @GET("list/data")
    Flowable<Response<NewsListRPB>> newsListRequest(@Query("size") int size);
}
