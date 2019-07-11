package com.renj.mvp.presenter

import com.renj.mvp.controller.IWebViewController
import com.renj.mvp.mode.bean.data.GeneralListBean
import com.renj.mvp.mode.db.DBHelper
import com.renj.rxsupport.rxpresenter.RxPresenter
import com.renj.rxsupport.utils.RxUtils
import io.reactivex.subscribers.ResourceSubscriber

/**
 * ======================================================================
 *
 *
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 *
 *
 * 创建时间：2019-07-11   15:08
 *
 *
 * 描述：
 *
 *
 * 修订历史：
 *
 *
 * ======================================================================
 */
class WebViewPresenter : RxPresenter<IWebViewController.IWebViewView>(), IWebViewController.IWebViewPresenter {
    override fun getCollectionStatus(pid: Int, id: Int) {
        addDisposable(mModelManager.getDBHelper(DBHelper::class.java)
                .getCollectionStatus(pid, id)
                .compose(RxUtils.newInstance().threadTransformer())
                .subscribeWith(object : ResourceSubscriber<Boolean>() {
                    override fun onComplete() {
                    }

                    override fun onNext(seeCount: Boolean?) {
                        mView.getCollectionStatusSuccess(seeCount!!)
                    }

                    override fun onError(t: Throwable?) {
                    }

                }))
    }

    override fun addSeeCount(generalListBean: GeneralListBean) {
        addDisposable(mModelManager.getDBHelper(DBHelper::class.java)
                .addSeeCount(generalListBean)
                .compose(RxUtils.newInstance().threadTransformer())
                .subscribeWith(object : ResourceSubscriber<Long>() {
                    override fun onComplete() {
                    }

                    override fun onNext(seeCount: Long?) {
                        mView.addSeeCountSuccess(seeCount!!)
                    }

                    override fun onError(t: Throwable?) {
                    }

                }))
    }

    override fun changeCollectionStatus(pid: Int, id: Int, collectionStatus: Boolean) {
        addDisposable(mModelManager.getDBHelper(DBHelper::class.java)
                .changeCollectionStatus(pid, id, collectionStatus)
                .compose(RxUtils.newInstance().threadTransformer())
                .subscribeWith(object : ResourceSubscriber<Boolean>() {
                    override fun onComplete() {
                    }

                    override fun onNext(collectionStatus: Boolean?) {
                        mView.changeCollectionStatusSuccess(collectionStatus!!)
                    }

                    override fun onError(t: Throwable?) {
                    }

                }))
    }

}
