package com.renj.found.presenter;

import android.support.annotation.NonNull;

import com.renj.common.mode.http.utils.CustomSubscriber;
import com.renj.common.mode.http.utils.ResponseTransformer;
import com.renj.found.controller.IFoundController;
import com.renj.found.mode.bean.response.FoundRPB;
import com.renj.found.mode.http.HttpHelper;
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
                .compose(new ResponseTransformer<FoundRPB>())
                .compose(RxUtils.newInstance().threadTransformer())
                .subscribeWith(new CustomSubscriber<FoundRPB>(loadingStyle, mView) {
                    @Override
                    public void onResult(@NonNull FoundRPB foundRPB) {
                        mView.foundRequestSuccess(loadingStyle, foundRPB);
                    }
                }));
    }
}
