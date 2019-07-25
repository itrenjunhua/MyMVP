package com.renj.classification.presenter

import com.renj.classification.controller.IClassificationListController
import com.renj.classification.mode.bean.response.GeneralListRPB
import com.renj.classification.mode.http.HttpHelper
import com.renj.common.mode.http.exception.NullDataException
import com.renj.common.mode.http.utils.CustomSubscriber
import com.renj.common.mode.http.utils.ResponseTransformer
import com.renj.rxsupport.rxpresenter.RxPresenter
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
    override fun classificationListRequest(loadingStyle: Int, requestCode: Int, pid: Int, pageNo: Int, pageSize: Int) {
        mView.showLoadingPage(loadingStyle, requestCode)
        addDisposable(mModelManager.getHttpHelper(HttpHelper::class.java)
                .classificationListRequest(pid, pageNo, pageSize)
                .compose(object : ResponseTransformer<GeneralListRPB>() {
                    override fun apply(upstream: Flowable<Response<GeneralListRPB>>?): Publisher<GeneralListRPB> {
                        return super.apply(upstream)
                    }

                    @Throws(NullDataException::class)
                    override fun onNullDataJudge(generalListRPB: GeneralListRPB?) {
                        if (ListUtils.isEmpty(generalListRPB?.data?.list))
                            throw NullDataException(generalListRPB!!)
                    }
                })
                .compose(RxUtils.newInstance().threadTransformer())
                .subscribeWith(object : CustomSubscriber<GeneralListRPB>(loadingStyle, requestCode, mView) {
                    override fun onResult(generalListRPB: GeneralListRPB) {
                        mView.classificationListRequestSuccess(requestCode, generalListRPB)
                    }
                }))
    }
}
