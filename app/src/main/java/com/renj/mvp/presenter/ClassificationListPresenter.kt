package com.renj.mvp.presenter

import com.renj.mvp.controller.IClassificationListController
import com.renj.mvp.mode.bean.response.GeneralListRPB
import com.renj.mvp.mode.http.HttpHelper
import com.renj.mvp.mode.http.exception.NullDataException
import com.renj.mvp.mode.http.utils.ResponseSubscriber
import com.renj.mvp.mode.http.utils.ResponseTransformer
import com.renj.rxsupport.rxpresenter.RxPresenter
import com.renj.rxsupport.utils.CustomSubscriber
import com.renj.rxsupport.utils.RxUtils
import com.renj.utils.collection.ListUtils
import io.reactivex.Flowable
import org.reactivestreams.Publisher
import retrofit2.Response

/**
 * ======================================================================
 *
 *
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 *
 *
 * 创建时间：2019-07-09   10:20
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
class ClassificationListPresenter : RxPresenter<IClassificationListController.IClassificationListView>(), IClassificationListController.IClassificationListPresenter {
    override fun classificationListRequest(loadingStyle: Int, pid: Int, pageNo: Int, pageSize: Int) {
        mView.showLoadingPage(loadingStyle)
        addDisposable(mModelManager.getHttpHelper(HttpHelper::class.java)
                .classificationListRequest(pid, pageNo, pageSize)
                .compose(object : ResponseTransformer<GeneralListRPB>() {
                    @Throws(NullDataException::class)
                    override fun onNullDataJudge(generalListRPB: GeneralListRPB?) {
                        if (ListUtils.isEmpty(generalListRPB?.data?.list))
                            throw NullDataException(generalListRPB!!)
                    }
                })
                .compose(RxUtils.threadTransformer<GeneralListRPB>())
                .subscribeWith(object : ResponseSubscriber<GeneralListRPB>(loadingStyle, mView) {
                    override fun onResult(generalListRPB: GeneralListRPB) {
                        mView.classificationListRequestSuccess(loadingStyle, generalListRPB)
                    }
                }))
    }
}
