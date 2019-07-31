package com.renj.found.controller;

import android.support.annotation.NonNull;

import com.renj.found.mode.bean.response.GeneralListRPB;
import com.renj.mvpbase.presenter.IBasePresenter;
import com.renj.mvpbase.view.IBaseView;
import com.renj.mvpbase.view.LoadingStyle;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：itrenjunhua@163.com
 * <p>
 * 创建时间：2019-07-08   17:03
 * <p>
 * 描述：分类列表
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public interface IClassificationListController {
    interface IClassificationListView extends IBaseView {
        void classificationListRequestSuccess(@LoadingStyle int loadingStyle, @NonNull GeneralListRPB generalListRPB);
    }

    interface IClassificationListPresenter extends IBasePresenter<IClassificationListView> {
        void classificationListRequest(@LoadingStyle int loadingStyle, int pid, int pageNo, int pageSize);
    }
}
