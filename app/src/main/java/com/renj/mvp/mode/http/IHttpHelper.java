package com.renj.mvp.mode.http;

import com.renj.mvp.mode.bean.HomeListRPB;
import com.renj.mvp.mode.bean.NewsListRPB;
import com.renj.mvpbase.mode.IMvpHttpHelper;

import io.reactivex.Flowable;
import retrofit2.Response;
import retrofit2.http.GET;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2018-08-17   10:43
 * <p>
 * 描述：APP 操作网络的帮助类接口，将所有的网络相关操作方法定义在此接口中
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public interface IHttpHelper extends IMvpHttpHelper {
    /**
     * 首页实时资讯数据
     */
    @GET("/LookUp")
    Flowable<Response<HomeListRPB>> homeListRequest();

    /**
     * 检索新闻数据
     */
    @GET("/Query")
    Flowable<Response<NewsListRPB>> newsListRequest(int size);
}
