package com.renj.found.presenter;

import android.support.annotation.NonNull;

import com.renj.found.controller.IClassificationController;
import com.renj.found.mode.bean.response.ClassificationRPB;
import com.renj.found.mode.http.HttpHelper;
import com.renj.common.mode.http.exception.NullDataException;
import com.renj.common.mode.http.utils.CustomSubscriber;
import com.renj.common.mode.http.utils.ResponseTransformer;
import com.renj.rxsupport.rxpresenter.RxPresenter;
import com.renj.rxsupport.utils.RxUtils;
import com.renj.utils.collection.ListUtils;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-07-08   17:05
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ClassificationPresenter extends RxPresenter<IClassificationController.IClassificationView>
        implements IClassificationController.IClassificationPresenter {
    @Override
    public void classificationRequest(int loadingStyle) {
        mView.showLoadingPage(loadingStyle);
        addDisposable(mModelManager.getHttpHelper(HttpHelper.class)
                .classificationDataRequest()
                .compose(new ResponseTransformer<ClassificationRPB>() {
                    @Override
                    protected void onNullDataJudge(ClassificationRPB classificationRPB) throws NullDataException {
                        if (ListUtils.isEmpty(classificationRPB.data))
                            mView.showEmptyDataPage(loadingStyle, classificationRPB);
                    }
                })
                .compose(RxUtils.newInstance().threadTransformer())
                .subscribeWith(new CustomSubscriber<ClassificationRPB>(loadingStyle, mView) {
                    @Override
                    public void onResult(@NonNull ClassificationRPB classificationRPB) {
                        mView.classificationRequestSuccess(loadingStyle, classificationRPB);
                    }
                }));
    }
}
