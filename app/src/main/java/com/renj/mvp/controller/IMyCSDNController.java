package com.renj.mvp.controller;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.renj.mvp.mode.bean.response.BannerAndNoticeRPB;
import com.renj.mvp.mode.bean.response.GeneralListRPB;
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
    interface IMyCSDNView extends IBaseView {
        void bannerRequestSuccess(@IntRange int requestCode, @NonNull BannerAndNoticeRPB bannerAndNoticeRPB);

        void listRequestSuccess(@IntRange int requestCode, @NonNull GeneralListRPB generalListRPB);
    }

    interface IMyCSDNPresenter extends IBasePresenter<IMyCSDNView> {
        void bannerRequest(@LoadingStyle int loadingStyle, @IntRange int requestCode);

        void listRequest(@LoadingStyle int loadingStyle, @IntRange int requestCode, int pageNo, int pageSize);
    }
}
