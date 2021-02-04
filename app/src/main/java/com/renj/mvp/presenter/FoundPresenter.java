package com.renj.mvp.presenter;

import android.support.annotation.NonNull;

import com.renj.mvp.controller.IFoundController;
import com.renj.mvp.mode.bean.response.FoundRPB;
import com.renj.mvp.mode.http.HttpHelper;
import com.renj.mvp.mode.http.utils.ResponseSubscriber;
import com.renj.mvp.mode.http.utils.ResponseTransformer;
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
public class FoundPresenter extends RxPresenter<IFoundController.IFoundView>
        implements IFoundController.IFoundPresenter {

    @Override
    public void foundRequest(int loadingStyle) {
        mView.showLoadingPage(loadingStyle);
        addDisposable(mModelManager.getHttpHelper(HttpHelper.class)
                .foundDataRequest()
                .compose(new ResponseTransformer<>())
                .compose(RxUtils.threadTransformer())
                .subscribeWith(new ResponseSubscriber<FoundRPB>(loadingStyle, mView) {
                    @Override
                    public void onResult(@NonNull FoundRPB foundRPB) {
                        mView.foundRequestSuccess(loadingStyle, foundRPB);
                    }
                }));
    }
}
