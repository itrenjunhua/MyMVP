package com.renj.mvp.mode.http;

import com.renj.mvp.mode.bean.response.BannerAndNoticeRPB;
import com.renj.mvp.mode.bean.response.ClassificationRPB;
import com.renj.mvp.mode.bean.response.FoundRPB;
import com.renj.mvp.mode.bean.response.GeneralListRPB;

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
    String BASE_URL = "http://129.28.203.98:8888/app/";

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

    /**
     * 发现页数据
     */
    @GET("found/index")
    Flowable<Response<FoundRPB>> foundDataRequest();

    /**
     * 分类目录
     */
    @GET("classification/index")
    Flowable<Response<ClassificationRPB>> classificationDataRequest();
}
