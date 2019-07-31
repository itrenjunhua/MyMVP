package com.renj.home.presenter;

import android.support.annotation.NonNull;

import com.renj.common.mode.http.exception.NullDataException;
import com.renj.common.mode.http.utils.CustomSubscriber;
import com.renj.common.mode.http.utils.ResponseTransformer;
import com.renj.home.controller.IMyCSDNController;
import com.renj.home.mode.bean.response.BannerAndNoticeRPB;
import com.renj.home.mode.bean.response.GeneralListRPB;
import com.renj.home.mode.http.HttpHelper;
import com.renj.rxsupport.rxpresenter.RxPresenter;
import com.renj.rxsupport.utils.RxUtils;
import com.renj.utils.collection.ListUtils;

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
public class MyCSDNPresenter extends RxPresenter<IMyCSDNController.IMyCSDNView>
        implements IMyCSDNController.IMyCSDNPresenter {

    @Override
    public void bannerRequest(int loadingStyle) {
        mView.showLoadingPage(loadingStyle);
        addDisposable(mModelManager.getHttpHelper(HttpHelper.class)
                .myCSDNBannerRequest()
                .compose(new ResponseTransformer<BannerAndNoticeRPB>())
                .compose(RxUtils.newInstance().<BannerAndNoticeRPB>threadTransformer())
                .subscribeWith(new CustomSubscriber<BannerAndNoticeRPB>(loadingStyle, mView) {
                    @Override
                    public void onResult(@NonNull BannerAndNoticeRPB bannerAndNoticeRPB) {
                        mView.bannerRequestSuccess(loadingStyle, bannerAndNoticeRPB);
                    }
                }));
    }

    @Override
    public void listRequest(int loadingStyle, int pageNo, int pageSize) {
        mView.showLoadingPage(loadingStyle);
        addDisposable(mModelManager.getHttpHelper(HttpHelper.class)
                .myCSDNListRequest(pageNo, pageSize)
                .compose(new ResponseTransformer<GeneralListRPB>() {
                    @Override
                    protected void onNullDataJudge(GeneralListRPB generalListRPB) throws NullDataException {
                        if (ListUtils.isEmpty(generalListRPB.data.list)) {
                            throw new NullDataException(generalListRPB);
                        }
                    }
                })
                .compose(RxUtils.newInstance().<GeneralListRPB>threadTransformer())
                .subscribeWith(new CustomSubscriber<GeneralListRPB>(loadingStyle, mView) {
                    @Override
                    public void onResult(@NonNull GeneralListRPB generalListRPB) {
                        mView.listRequestSuccess(loadingStyle, generalListRPB);
                    }
                }));
    }
}
