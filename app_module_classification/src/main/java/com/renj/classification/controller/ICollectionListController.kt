package com.renj.classification.controller

import com.renj.common.mode.bean.dp.ListSeeAndCollectionRDB
import com.renj.mvpbase.presenter.IBasePresenter
import com.renj.mvpbase.view.IBaseView
import com.renj.mvpbase.view.LoadingStyle

/**
 * ======================================================================
 *
 *
 * 作者：Renj
 * 邮箱：itrenjunhua@163.com
 *
 *
 * 创建时间：2019-07-11   10:07
 *
 *
 * 描述：收藏列表
 *
 *
 * 修订历史：
 *
 *
 * ======================================================================
 */
interface ICollectionListController {
    interface ICollectionListView : IBaseView {
        fun listResponseSuccess(@LoadingStyle loadingStyle: Int, requestCode: Int, collectionRDB: ListSeeAndCollectionRDB)
    }

    interface ICollectionListPresenter : IBasePresenter<ICollectionListView> {
        fun listResponse(@LoadingStyle loadingStyle: Int, requestCode: Int, pagNo: Int, pageSize: Int)
    }
}
