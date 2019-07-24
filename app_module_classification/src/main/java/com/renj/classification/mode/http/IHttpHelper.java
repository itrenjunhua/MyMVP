package com.renj.classification.mode.http;

import com.renj.classification.mode.bean.response.ClassificationRPB;
import com.renj.classification.mode.bean.response.GeneralListRPB;
import com.renj.mvpbase.mode.IMvpHttpHelper;

import io.reactivex.Flowable;
import retrofit2.Response;

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
     * 我的CSDN 列表数据
     */
    Flowable<Response<GeneralListRPB>> myCSDNListRequest(int pageNo, int pageSize);


    /**
     * 我的GitHub 列表数据
     */
    Flowable<Response<GeneralListRPB>> myGitHubListRequest(int pageNo, int pageSize);

    /**
     * 分类目录
     */
    Flowable<Response<ClassificationRPB>> classificationDataRequest();

    /**
     * 分类列表
     */
    Flowable<Response<GeneralListRPB>> classificationListRequest(int pid, int pageNo, int pageSize);
}
