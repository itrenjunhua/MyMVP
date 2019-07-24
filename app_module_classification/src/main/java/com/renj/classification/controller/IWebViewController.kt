package com.renj.mvp.controller

import com.renj.mvp.mode.bean.data.GeneralListBean
import com.renj.mvpbase.presenter.IBasePresenter
import com.renj.mvpbase.view.IBaseView

/**
 * ======================================================================
 *
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 *
 * 创建时间：2019-07-11   15:11
 *
 * 描述：
 *
 * 修订历史：
 *
 * ======================================================================
 */
interface IWebViewController {
    interface IWebViewView : IBaseView {
        /**
         * 参数为最新查看次数
         */
        fun addSeeCountSuccess(seeCount: Long)

        /**
         * 参数表示是否修改成功
         */
        fun changeCollectionStatusSuccess(collectionStatus: Boolean)

        fun getCollectionStatusSuccess(boolean: Boolean)
    }

    interface IWebViewPresenter : IBasePresenter<IWebViewView> {
        fun addSeeCount(generalListBean: GeneralListBean)

        fun changeCollectionStatus(pid: Int, id: Int, collectionStatus: Boolean)

        fun getCollectionStatus(pid: Int, id: Int)
    }
}