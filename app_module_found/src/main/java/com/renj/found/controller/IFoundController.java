package com.renj.found.controller;

import android.support.annotation.NonNull;

import com.renj.found.mode.bean.response.FoundRPB;
import com.renj.mvpbase.presenter.IBasePresenter;
import com.renj.mvpbase.view.IBaseView;
import com.renj.mvpbase.view.LoadingStyle;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-04-17   14:27
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public interface IFoundController {
    interface IFoundView extends IBaseView {
        void foundRequestSuccess(@LoadingStyle int loadingStyle, @NonNull FoundRPB foundRPB);
    }

    interface IFoundPresenter extends IBasePresenter<IFoundView> {
        void foundRequest(@LoadingStyle int loadingStyle);
    }
}
