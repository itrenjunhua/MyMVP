package com.renj.mvp.presenter;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.renj.common.utils.CheckUtils;
import com.renj.mvp.controller.IMyCSDNController;
import com.renj.mvp.mode.bean.HomeListRPB;
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
 * 创建时间：2019-04-16   16:50
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class MyCSDNPresenter extends RxPresenter<IMyCSDNController.IHomeView>
        implements IMyCSDNController.IHomePresenter {
    @Override
    public void homeListRequest(@LoadingStyle final int loadingStyle, @IntRange final int requestCode) {
        mView.showLoadingPage(loadingStyle, requestCode);
        addDisposable(mModelManager.getHttpHelper(HttpHelper.class)
                .homeListRequest()
                .compose(new ResponseTransformer<HomeListRPB>() {
                    @Override
                    protected void onNullDataJudge(HomeListRPB homeListRPB) throws NullDataException {
                        if (CheckUtils.isNull(homeListRPB.data)) {
                            throw new NullDataException(homeListRPB);
                        }
                    }
                })
                .compose(RxUtils.newInstance().<HomeListRPB>threadTransformer())
                .subscribeWith(new CustomSubscriber<HomeListRPB>(loadingStyle, requestCode, mView) {
                    @Override
                    public void onResult(@NonNull HomeListRPB homeListRPB) {
                        mView.homeListRequestSuccess(requestCode, homeListRPB);
                    }
                }));
    }
}
