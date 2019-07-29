package com.renj.home.mode.http;


import com.renj.home.mode.bean.response.BannerAndNoticeRPB;
import com.renj.home.mode.bean.response.GeneralListRPB;

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
    /**
     * 我的CSDN banner和公告数据
     */
    @GET("csdn/index")
    Flowable<Response<BannerAndNoticeRPB>> myCSDNBannerRequest();

    /**
     * 我的CSDN 列表数据
     */
    @GET("csdn/list")
    Flowable<Response<GeneralListRPB>> myCSDNListRequest(@Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    /**
     * 我的GitHub banner和公告数据
     */
    @GET("github/index")
    Flowable<Response<BannerAndNoticeRPB>> myGitHubBannerRequest();

    /**
     * 我的GitHub 列表数据
     */
    @GET("github/list")
    Flowable<Response<GeneralListRPB>> myGitHubListRequest(@Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

}
