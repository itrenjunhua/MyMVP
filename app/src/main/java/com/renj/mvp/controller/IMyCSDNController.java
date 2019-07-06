package com.renj.mvp.controller;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.renj.mvp.mode.bean.HomeListRPB;
import com.renj.mvpbase.presenter.IBasePresenter;
import com.renj.mvpbase.view.IBaseView;
import com.renj.mvpbase.view.LoadingStyle;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-04-16   16:48
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public interface IMyCSDNController {
    interface IHomeView extends IBaseView {
        void homeListRequestSuccess(@IntRange int requestCode, @NonNull HomeListRPB homeListRPB);
    }

    interface IHomePresenter extends IBasePresenter<IHomeView> {
        void homeListRequest(@LoadingStyle int loadingStyle, @IntRange int requestCode);
    }
}
