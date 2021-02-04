package com.renj.mvp.mode.http;

import com.renj.httplibrary.RetrofitUtil;
import com.renj.mvp.mode.bean.response.BannerAndNoticeRPB;
import com.renj.mvp.mode.bean.response.ClassificationRPB;
import com.renj.mvp.mode.bean.response.FoundRPB;
import com.renj.mvp.mode.bean.response.GeneralListRPB;

import io.reactivex.Observable;

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
    public Observable<BannerAndNoticeRPB> myCSDNBannerRequest() {
        return mApiServer.myCSDNBannerRequest();
    }

    @Override
    public Observable<GeneralListRPB> myCSDNListRequest(int pageNo, int pageSize) {
        return mApiServer.myCSDNListRequest(pageNo, pageSize);
    }

    @Override
    public Observable<BannerAndNoticeRPB> myGitHubBannerRequest() {
        return mApiServer.myGitHubBannerRequest();
    }

    @Override
    public Observable<GeneralListRPB> myGitHubListRequest(int pageNo, int pageSize) {
        return mApiServer.myGitHubListRequest(pageNo, pageSize);
    }

    public Observable<FoundRPB> foundDataRequest() {
        return mApiServer.foundDataRequest();
    }

    @Override
    public Observable<ClassificationRPB> classificationDataRequest() {
        return mApiServer.classificationDataRequest();
    }

    @Override
    public Observable<GeneralListRPB> classificationListRequest(int pid, int pageNo, int pageSize) {
        return mApiServer.classificationListRequest(pid, pageNo, pageSize);
    }
}
