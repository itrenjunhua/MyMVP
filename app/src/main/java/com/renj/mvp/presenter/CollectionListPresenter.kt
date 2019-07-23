package com.renj.mvp.presenter

import com.renj.mvp.controller.ICollectionListController
import com.renj.mvp.mode.db.DBHelper
import com.renj.mvp.mode.db.bean.ListSeeAndCollectionRDB
import com.renj.mvpbase.view.LoadingStyle
import com.renj.rxsupport.rxpresenter.RxPresenter
import com.renj.rxsupport.utils.RxUtils
import com.renj.utils.collection.ListUtils
import io.reactivex.subscribers.ResourceSubscriber

/**
 * ======================================================================
 *
 * 作者：Renj
 * 邮箱：itrenjunhua@163.com
 *
 * 创建时间：2019-07-11   10:21
 *
 * 描述：
 *
 * 修订历史：
 *
 * ======================================================================
 */
class CollectionListPresenter : RxPresenter<ICollectionListController.ICollectionListView>(), ICollectionListController.ICollectionListPresenter {
    override fun listResponse(@LoadingStyle loadingStyle: Int, requestCode: Int, pagNo: Int, pageSize: Int) {
        mView.showLoadingPage(loadingStyle, requestCode)
        addDisposable(mModelManager.getDBHelper(DBHelper::class.java)
                .getCollectionList(pagNo, pageSize)
                .compose(RxUtils.newInstance().threadTransformer())
                .subscribeWith(object : ResourceSubscriber<ListSeeAndCollectionRDB>() {
                    override fun onComplete() {

                    }

                    override fun onNext(collectionRDB: ListSeeAndCollectionRDB?) {
                        if (ListUtils.isEmpty(collectionRDB?.list)) {
                            mView.showEmptyDataPage(loadingStyle, requestCode, collectionRDB!!)
                        } else {
                            mView.listResponseSuccess(loadingStyle, requestCode, collectionRDB!!)
                            mView.showContentPage(loadingStyle, requestCode, collectionRDB)
                        }
                    }

                    override fun onError(t: Throwable?) {
                        mView.showErrorPage(loadingStyle, 0, t)
                    }

                }))
    }
}