package com.renj.classification.mode.http;

import com.renj.httplibrary.RetrofitUtil;
import com.renj.classification.mode.bean.response.ClassificationRPB;
import com.renj.classification.mode.bean.response.GeneralListRPB;

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
 * 描述：APP 操作网络的帮助类，实现 {@link IHttpHelper} 接口
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class HttpHelper implements IHttpHelper {
    private ApiServer mApiServer = RetrofitUtil.newInstance().getApiService(ApiServer.class);

    @Override
    public Flowable<Response<GeneralListRPB>> myCSDNListRequest(int pageNo, int pageSize) {
        return mApiServer.myCSDNListRequest(pageNo, pageSize);
    }

    @Override
    public Flowable<Response<GeneralListRPB>> myGitHubListRequest(int pageNo, int pageSize) {
        return mApiServer.myGitHubListRequest(pageNo, pageSize);
    }

    @Override
    public Flowable<Response<ClassificationRPB>> classificationDataRequest() {
        return mApiServer.classificationDataRequest();
    }

    @Override
    public Flowable<Response<GeneralListRPB>> classificationListRequest(int pid, int pageNo, int pageSize) {
        return mApiServer.classificationListRequest(pid, pageNo, pageSize);
    }
}
