package com.renj.mvp.presenter;

import android.support.annotation.NonNull;

import com.renj.common.utils.ListUtils;
import com.renj.mvp.controller.IFoundController;
import com.renj.mvp.mode.bean.NewsListRPB;
import com.renj.mvp.mode.http.HttpHelper;
import com.renj.mvp.mode.http.exception.NullDataException;
import com.renj.mvp.mode.http.utils.CustomSubscriber;
import com.renj.mvp.mode.http.utils.ResponseTransformer;
import com.renj.mvpbase.view.LoadingStyle;
import com.renj.rxsupport.rxpresenter.RxPresenter;
import com.renj.rxsupport.utils.RxUtils;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-04-17   14:29
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class FoundPresenter extends RxPresenter<IFoundController.INewsView>
        implements IFoundController.INewsPresenter {
    @Override
    public void newsListRequest(@LoadingStyle int loadingStyle, final int requestCode, int size) {
        mView.showLoadingPage(loadingStyle, requestCode);
        addDisposable(mModelManager.getHttpHelper(HttpHelper.class)
                .newsListRequest(size)
                .compose(new ResponseTransformer<NewsListRPB>() {
                    @Override
                    protected void onNullDataJudge(NewsListRPB newsListRPB) throws NullDataException {
                        if (ListUtils.isEmpty(newsListRPB.data)) {
                            throw new NullDataException(newsListRPB);
                        }
                    }
                })
                .compose(RxUtils.newInstance().<NewsListRPB>threadTransformer())
                .subscribeWith(new CustomSubscriber<NewsListRPB>(loadingStyle, requestCode, mView) {
                    @Override
                    public void onResult(@NonNull NewsListRPB newsListRPB) {
                        mView.newsListRequestSuccess(requestCode, newsListRPB);
                    }
                }));
    }
}
