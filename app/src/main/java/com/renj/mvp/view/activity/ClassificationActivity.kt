package com.renj.mvp.view.activity

import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.renj.mvp.R
import com.renj.mvp.controller.IClassificationController
import com.renj.mvp.mode.bean.response.ClassificationRPB
import com.renj.mvp.presenter.ClassificationPresenter
import com.renj.mvp.utils.MyCommonUtils
import com.renj.mvp.view.cell.CellFactory
import com.renj.mvp.view.cell.ClassificationCell
import com.renj.mvpbase.view.LoadingStyle
import com.renj.pagestatuscontroller.IRPageStatusController
import com.renj.pagestatuscontroller.annotation.RPageStatus
import com.renj.recycler.adapter.RecyclerAdapter
import com.renj.rxsupport.rxview.RxBasePresenterActivity
import kotlinx.android.synthetic.main.classification_activity.*

/**
 * ======================================================================
 *
 *
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 *
 *
 * 创建时间：2019-07-08   17:10
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
class ClassificationActivity : RxBasePresenterActivity<ClassificationPresenter>(), IClassificationController.IClassificationView {
    private var recyclerAdapter: RecyclerAdapter<ClassificationCell>? = null

    companion object {
        const val REQUEST_CODE_REFRESH = 0
    }

    override fun getLayoutId(): Int {
        return R.layout.classification_activity
    }

    override fun initData() {
        setPageTitle(R.string.title_classification)
        setPageBack(true, false, null)
        initSwipeToLoadLayout()
        initRecyclerView()

        mPresenter.classificationRequest(LoadingStyle.LOADING_PAGE, REQUEST_CODE_REFRESH)
    }

    private fun initSwipeToLoadLayout() {
        swipe_toLoad_layout.setOnRefreshListener { mPresenter.classificationRequest(LoadingStyle.LOADING_REFRESH, REQUEST_CODE_REFRESH) }
    }

    private fun initRecyclerView() {
        recyclerAdapter = RecyclerAdapter()
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        swipe_target.layoutManager = linearLayoutManager
        swipe_target.adapter = recyclerAdapter
        swipe_target.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    }

    override fun classificationRequestSuccess(requestCode: Int, classificationRPB: ClassificationRPB) {
        recyclerAdapter?.setData(CellFactory.createClassificationCell(classificationRPB.data))
    }

    override fun handlerPageLoadException(iRPageStatusController: IRPageStatusController<out IRPageStatusController<*>>?, pageStatus: Int, `object`: Any?, view: View?, viewId: Int) {
        if (pageStatus == RPageStatus.ERROR && viewId == R.id.tv_error) {
            mPresenter.classificationRequest(LoadingStyle.LOADING_PAGE, REQUEST_CODE_REFRESH)
        } else if (pageStatus == RPageStatus.NET_WORK && viewId == R.id.tv_reload) {
            // 此处修改页面状态是因为在 MyApplication 中指定了当网络异常时点击不自动修改为 loading 状态
            rPageStatusController.changePageStatus(RPageStatus.LOADING)
            mPresenter.classificationRequest(LoadingStyle.LOADING_PAGE, REQUEST_CODE_REFRESH)
        } else if (pageStatus == RPageStatus.NET_WORK && viewId == R.id.tv_net_work) {
            MyCommonUtils.openNetWorkActivity(this)
        }
    }

    override fun handlerResultOtherStyle(status: Int, loadingStyle: Int, requestCode: Int, `object`: Any?) {
        swipe_toLoad_layout.isRefreshing = false
    }
}
