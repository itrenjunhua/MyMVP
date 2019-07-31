package com.renj.home.controller;

import android.support.annotation.NonNull;

import com.renj.home.mode.bean.response.BannerAndNoticeRPB;
import com.renj.home.mode.bean.response.GeneralListRPB;
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
public interface IMyGitHubController {
    interface IMyGithubView extends IBaseView {
        void bannerRequestSuccess(@LoadingStyle int loadingStyle, @NonNull BannerAndNoticeRPB bannerAndNoticeRPB);

        void listRequestSuccess(@LoadingStyle int loadingStyle, @NonNull GeneralListRPB generalListRPB);
    }

    interface IMyGitHubPresenter extends IBasePresenter<IMyGithubView> {
        void bannerRequest(@LoadingStyle int loadingStyle);

        void listRequest(@LoadingStyle int loadingStyle, int pageNo, int pageSize);
    }
}
