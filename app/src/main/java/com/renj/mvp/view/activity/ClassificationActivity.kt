package com.renj.mvp.view.activity

import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.renj.mvp.R
import com.renj.mvp.controller.IClassificationController
import com.renj.mvp.mode.bean.response.ClassificationRPB
import com.renj.mvp.presenter.ClassificationPresenter
import com.renj.mvp.utils.MyCommonUtils
import com.renj.mvp.view.cell.ClassificationCell
import com.renj.mvpbase.view.LoadingStyle
import com.renj.pagestatuscontroller.IRPageStatusController
import com.renj.pagestatuscontroller.annotation.RPageStatus
import com.renj.rxsupport.rxview.RxBasePresenterActivity
import com.renj.view.recyclerview.adapter.BaseRecyclerCell
import com.renj.view.recyclerview.adapter.RecyclerAdapter
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
    private var recyclerAdapter: RecyclerAdapter<ClassificationRPB>? = null

    override fun getLayoutId(): Int {
        return R.layout.classification_activity
    }

    override fun initView(contentView: View?) {
    }

    override fun initData() {
        setPageTitle(R.string.title_classification)
        initSwipeToLoadLayout()
        initRecyclerView()

        mPresenter.classificationRequest(LoadingStyle.LOADING_PAGE)
    }

    private fun initSwipeToLoadLayout() {
        swipe_toLoad_layout.setOnRefreshListener { mPresenter.classificationRequest(LoadingStyle.LOADING_REFRESH) }
    }

    private fun initRecyclerView() {
        recyclerAdapter = object : RecyclerAdapter<ClassificationRPB>() {
            override fun <C : BaseRecyclerCell<ClassificationRPB>?> getRecyclerCell(itemTypeValue: Int): C {
                return ClassificationCell() as C
            }
        }
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        swipe_target.layoutManager = linearLayoutManager
        swipe_target.adapter = recyclerAdapter
        swipe_target.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    }

    override fun classificationRequestSuccess(requestCode: Int, classificationRPB: ClassificationRPB) {
        recyclerAdapter?.setData(classificationRPB.data)
    }

    override fun handlerPageLoadException(iRPageStatusController: IRPageStatusController<out IRPageStatusController<*>>?, pageStatus: Int, `object`: Any?, view: View?, viewId: Int) {
        if (pageStatus == RPageStatus.ERROR && viewId == R.id.tv_error) {
            mPresenter.classificationRequest(LoadingStyle.LOADING_PAGE)
        } else if (pageStatus == RPageStatus.NET_WORK && viewId == R.id.tv_reload) {
            // 此处修改页面状态是因为在 MyApplication 中指定了当网络异常时点击不自动修改为 loading 状态
            rPageStatusController.changePageStatus(RPageStatus.LOADING)
            mPresenter.classificationRequest(LoadingStyle.LOADING_PAGE)
        } else if (pageStatus == RPageStatus.NET_WORK && viewId == R.id.tv_net_work) {
            MyCommonUtils.openNetWorkActivity(this)
        }
    }

    override fun showCustomResultPage(status: Int, loadingStyle: Int, `object`: Any?) {
        swipe_toLoad_layout.isRefreshing = false
    }
}
