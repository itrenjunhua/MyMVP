package com.renj.found.view.fragment

import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.renj.arouter.ARouterPath
import com.renj.common.view.cell.CommonCellFactory
import com.renj.found.R
import com.renj.found.controller.IFoundController
import com.renj.found.mode.bean.response.FoundRPB
import com.renj.found.presenter.FoundPresenter
import com.renj.found.view.cell.CellFactory
import com.renj.mvpbase.view.LoadingStyle
import com.renj.pagestatuscontroller.IRPageStatusController
import com.renj.pagestatuscontroller.annotation.RPageStatus
import com.renj.rxsupport.rxview.RxBasePresenterFragment
import com.renj.utils.net.NetWorkUtils
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
@Route(path = ARouterPath.PATH_FRAGMENT_FOUND, group = ARouterPath.GROUP_MAIN)
class FoundFragment : RxBasePresenterFragment<FoundPresenter>(), IFoundController.IFoundView {

    private var recyclerAdapter: RecyclerAdapter<IRecyclerCell<*>>? = null

    companion object {
        const val REQUEST_CODE_REFRESH = 1
    }

    override fun getLayoutId(): Int {
        return R.layout.found_fragment
    }

    override fun initData() {
        initSwipeToLoadLayout()
        initRecyclerView()

        mPresenter.foundRequest(LoadingStyle.LOADING_PAGE, REQUEST_CODE_REFRESH)
    }

    private fun initSwipeToLoadLayout() {
        swipe_toLoad_layout.setOnRefreshListener { mPresenter.foundRequest(LoadingStyle.LOADING_REFRESH, REQUEST_CODE_REFRESH) }
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
        cells.add(CommonCellFactory.createSegmentationCell(ResUtils.getString(R.string.found_segmentation_name)) as IRecyclerCell<*>)
        cells.addAll(CellFactory.createGeneralListCell(foundRPB.data.beanList) as List<IRecyclerCell<*>>)
        cells.add(CellFactory.createSeeMoreCell() as IRecyclerCell<*>)

        recyclerAdapter?.setData(cells)
    }

    override fun handlerPageLoadException(iRPageStatusController: IRPageStatusController<*>, pageStatus: Int, `object`: Any, view: View, viewId: Int) {
        if (pageStatus == RPageStatus.ERROR && viewId == R.id.tv_error) {
            mPresenter.foundRequest(LoadingStyle.LOADING_PAGE, REQUEST_CODE_REFRESH)
        } else if (pageStatus == RPageStatus.NET_WORK && viewId == R.id.tv_reload) {
            // 此处修改页面状态是因为在 BaseApplication 中指定了当网络异常时点击不自动修改为 loading 状态
            rPageStatusController.changePageStatus(RPageStatus.LOADING)
            mPresenter.foundRequest(LoadingStyle.LOADING_PAGE, REQUEST_CODE_REFRESH)
        } else if (pageStatus == RPageStatus.NET_WORK && viewId == R.id.tv_net_work) {
            NetWorkUtils.openNetWorkActivity()
        }
    }

    override fun handlerResultOtherStyle(status: Int, loadingStyle: Int, requestCode: Int, `object`: Any?) {
        swipe_toLoad_layout.isRefreshing = false
    }

}
