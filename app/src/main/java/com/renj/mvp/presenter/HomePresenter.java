package com.renj.mvp.presenter;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.renj.common.utils.ListUtils;
import com.renj.mvp.controller.IHomeController;
import com.renj.mvp.mode.bean.HomeListRPB;
import com.renj.mvp.mode.http.HttpHelper;
import com.renj.mvp.mode.http.exception.NullDataException;
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
 * 创建时间：2019-04-16   16:50
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class HomePresenter extends RxPresenter<IHomeController.IHomeView>
        implements IHomeController.IHomePresenter {
    @Override
    public void homeListRequest(@IntRange final int requestCode) {
        addDisposable(mModelManager.getHttpHelper(HttpHelper.class)
                .homeListRequest()
                .compose(new ResponseTransformer<HomeListRPB>() {
                    @Override
                    protected void onNullDataJudge(HomeListRPB homeListRPB) throws NullDataException {
                        if (ListUtils.isEmpty(homeListRPB.result)) {
                            throw new NullDataException(homeListRPB);
                        }
                    }
                })
                .compose(RxUtils.newInstance().<HomeListRPB>threadTransformer())
                .subscribeWith(new CustomSubscriber<HomeListRPB>(requestCode, mView) {
                    @Override
                    public void onResult(@NonNull HomeListRPB homeListRPB) {
                        mView.homeListRequestSuccess(requestCode, homeListRPB);
                    }
                }));
    }
}
