package com.renj.mvp.mode.http;

import com.renj.mvp.mode.bean.HomeListRPB;

import io.reactivex.Flowable;
import retrofit2.Response;
import retrofit2.http.GET;

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
    String BASE_URL = "http://api.avatardata.cn/ActNews/";

    /**
     * 首页实时资讯数据
     */
    @GET("LookUp")
    Flowable<Response<HomeListRPB>> homeListRequest();
}
