package com.renj.classification.presenter;

import android.support.annotation.NonNull;

import com.renj.mvp.controller.IClassificationController;
import com.renj.mvp.mode.bean.response.ClassificationRPB;
import com.renj.mvp.mode.http.HttpHelper;
import com.renj.mvp.mode.http.exception.NullDataException;
import com.renj.mvp.mode.http.utils.CustomSubscriber;
import com.renj.mvp.mode.http.utils.ResponseTransformer;
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
    public void classificationRequest(int loadingStyle, int requestCode) {
        mView.showLoadingPage(loadingStyle, requestCode);
        addDisposable(mModelManager.getHttpHelper(HttpHelper.class)
                .classificationDataRequest()
                .compose(new ResponseTransformer<ClassificationRPB>() {
                    @Override
                    protected void onNullDataJudge(ClassificationRPB classificationRPB) throws NullDataException {
                        if (ListUtils.isEmpty(classificationRPB.data))
                            mView.showEmptyDataPage(loadingStyle, requestCode, classificationRPB);
                    }
                })
                .compose(RxUtils.newInstance().threadTransformer())
                .subscribeWith(new CustomSubscriber<ClassificationRPB>(loadingStyle, requestCode, mView) {
                    @Override
                    public void onResult(@NonNull ClassificationRPB classificationRPB) {
                        mView.classificationRequestSuccess(requestCode, classificationRPB);
                    }
                }));
    }
}
