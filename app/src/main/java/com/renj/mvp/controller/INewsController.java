package com.renj.mvp.controller;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.renj.mvp.mode.bean.NewsListRPB;
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
public interface INewsController {
    interface INewsView extends IBaseView {
        void newsListRequestSuccess(@IntRange int requestCode, @NonNull NewsListRPB newsListRPB);
    }

    interface INewsPresenter extends IBasePresenter<INewsView> {
        void newsListRequest(@LoadingStyle int loadingStyle, @IntRange int requestCode, @NonNull String keyword);
    }
}
