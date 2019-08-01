package com.renj.mvp.view.fragment

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.renj.mvp.R
import com.renj.mvp.controller.IFoundController
import com.renj.mvp.mode.bean.response.FoundRPB
import com.renj.mvp.presenter.FoundPresenter
import com.renj.mvp.utils.MyCommonUtils
import com.renj.mvp.view.cell.CellFactory
import com.renj.mvpbase.view.LoadingStyle
import com.renj.pagestatuscontroller.IRPageStatusController
import com.renj.pagestatuscontroller.annotation.RPageStatus
import com.renj.rxsupport.rxview.RxBasePresenterFragment
import com.renj.utils.res.ResUtils
import com.renj.view.recyclerview.adapter.IRecyclerCell
import com.renj.view.recyclerview.adapter.RecyclerAdapter
import kotlinx.android.synthetic.main.found_fragment.*

/**
 * ======================================================================
 *
 *
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 *
 *
 * 创建时间：2019-04-15   16:57
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
class FoundFragment : RxBasePresenterFragment<FoundPresenter>(), IFoundController.IFoundView {

    private var recyclerAdapter: RecyclerAdapter<IRecyclerCell<*>>? = null

    companion object {
        fun newInstance(): FoundFragment {
            val args = Bundle()
            val fragment = FoundFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.found_fragment
    }

    override fun initData() {
        initSwipeToLoadLayout()
        initRecyclerView()

        mPresenter.foundRequest(LoadingStyle.LOADING_PAGE)
    }

    private fun initSwipeToLoadLayout() {
        swipe_toLoad_layout.setOnRefreshListener { mPresenter.foundRequest(LoadingStyle.LOADING_REFRESH) }
    }

    private fun initRecyclerView() {
        recyclerAdapter = RecyclerAdapter()
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        swipe_target.layoutManager = linearLayoutManager
        swipe_target.adapter = recyclerAdapter
        swipe_target.addItemDecoration(DividerItemDecoration(context!!, LinearLayoutManager.VERTICAL))
    }

    override fun foundRequestSuccess(requestCode: Int, foundRPB: FoundRPB) {
        var cells = ArrayList<IRecyclerCell<*>>()

        cells.add(CellFactory.createBannerCell(foundRPB.data.banners) as IRecyclerCell<*>)
        cells.add(CellFactory.createSegmentationCell(ResUtils.getString(R.string.found_segmentation_name)))
        cells.addAll(CellFactory.createGeneralListCell(foundRPB.data.beanList) as List<IRecyclerCell<*>>)
        cells.add(CellFactory.createSeeMoreCell() as IRecyclerCell<*>)

        recyclerAdapter?.setData(cells)
    }

    override fun handlerPageLoadException(iRPageStatusController: IRPageStatusController<*>, pageStatus: Int, `object`: Any, view: View, viewId: Int) {
        if (pageStatus == RPageStatus.ERROR && viewId == R.id.tv_error) {
            mPresenter.foundRequest(LoadingStyle.LOADING_PAGE)
        } else if (pageStatus == RPageStatus.NET_WORK && viewId == R.id.tv_reload) {
            // 此处修改页面状态是因为在 MyApplication 中指定了当网络异常时点击不自动修改为 loading 状态
            rPageStatusController.changePageStatus(RPageStatus.LOADING)
            mPresenter.foundRequest(LoadingStyle.LOADING_PAGE)
        } else if (pageStatus == RPageStatus.NET_WORK && viewId == R.id.tv_net_work) {
            MyCommonUtils.openNetWorkActivity(activity)
        }
    }

    override fun showCustomResultPage(status: Int, loadingStyle: Int, `object`: Any?) {
        swipe_toLoad_layout.isRefreshing = false
    }

}
