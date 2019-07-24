package com.renj.classification.mode.http;

import com.renj.classification.mode.bean.response.ClassificationRPB;
import com.renj.classification.mode.bean.response.GeneralListRPB;

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
     * 我的CSDN 列表数据
     */
    @GET("csdn/list")
    Flowable<Response<GeneralListRPB>> myCSDNListRequest(@Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    /**
     * 我的GitHub 列表数据
     */
    @GET("github/list")
    Flowable<Response<GeneralListRPB>> myGitHubListRequest(@Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    /**
     * 分类目录
     */
    @GET("classification/index")
    Flowable<Response<ClassificationRPB>> classificationDataRequest();

    /**
     * 分类列表
     */
    @GET("classification/list")
    Flowable<Response<GeneralListRPB>> classificationListRequest(@Query("pid") int pid, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);
}
