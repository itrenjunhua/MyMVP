package com.renj.classification.presenter;

import android.support.annotation.NonNull;

import com.renj.mvp.controller.IFoundController;
import com.renj.mvp.mode.bean.response.FoundRPB;
import com.renj.mvp.mode.http.HttpHelper;
import com.renj.mvp.mode.http.utils.CustomSubscriber;
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
    public void foundRequest(int loadingStyle, int requestCode) {
        mView.showLoadingPage(loadingStyle, requestCode);
        addDisposable(mModelManager.getHttpHelper(HttpHelper.class)
                .foundDataRequest()
                .compose(new ResponseTransformer<FoundRPB>())
                .compose(RxUtils.newInstance().threadTransformer())
                .subscribeWith(new CustomSubscriber<FoundRPB>(loadingStyle, requestCode, mView) {
                    @Override
                    public void onResult(@NonNull FoundRPB foundRPB) {
                        mView.foundRequestSuccess(requestCode, foundRPB);
                    }
                }));
    }
}
