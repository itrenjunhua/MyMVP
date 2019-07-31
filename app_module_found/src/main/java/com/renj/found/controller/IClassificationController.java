package com.renj.found.controller;

import android.support.annotation.NonNull;

import com.renj.found.mode.bean.response.ClassificationRPB;
import com.renj.mvpbase.presenter.IBasePresenter;
import com.renj.mvpbase.view.IBaseView;
import com.renj.mvpbase.view.LoadingStyle;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-07-08   17:03
 * <p>
 * 描述：分类目录
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public interface IClassificationController {
    interface IClassificationView extends IBaseView {
        void classificationRequestSuccess(@LoadingStyle int loadingStyle, @NonNull ClassificationRPB classificationRPB);
    }

    interface IClassificationPresenter extends IBasePresenter<IClassificationView> {
        void classificationRequest(@LoadingStyle int loadingStyle);
    }
}
