package com.renj.mvp.mode.http;

import android.support.annotation.NonNull;

import com.renj.httplibrary.RetrofitUtil;
import com.renj.mvp.mode.bean.HomeListRPB;
import com.renj.mvp.mode.bean.NewsListRPB;

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

    /**
     * 首页实时资讯数据
     */
    @Override
    public Flowable<Response<HomeListRPB>> homeListRequest() {
        return mApiServer.homeListRequest();
    }

    /**
     * 检索新闻数据
     */
    @Override
    public Flowable<Response<NewsListRPB>> newsListRequest(@NonNull String keyword) {
        return mApiServer.newsListRequest(keyword);
    }
}
