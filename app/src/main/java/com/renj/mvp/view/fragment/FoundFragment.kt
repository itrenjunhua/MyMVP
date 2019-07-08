package com.renj.mvp.view.fragment

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.BindView
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.renj.common.utils.ResUtils
import com.renj.daggersupport.DaggerSupportPresenterFragment
import com.renj.mvp.R
import com.renj.mvp.controller.IFoundController
import com.renj.mvp.mode.bean.response.FoundRPB
import com.renj.mvp.presenter.FoundPresenter
import com.renj.mvp.view.cell.CellFactory
import com.renj.mvpbase.view.LoadingStyle
import com.renj.pagestatuscontroller.IRPageStatusController
import com.renj.pagestatuscontroller.annotation.RPageStatus
import com.renj.recycler.adapter.IRecyclerCell
import com.renj.recycler.adapter.RecyclerAdapter

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
class FoundFragment : DaggerSupportPresenterFragment<FoundPresenter>(), IFoundController.IFoundView {

    @BindView(R.id.swipe_toLoad_layout)
    lateinit var swipeToLoadLayout: SwipeToLoadLayout
    @BindView(R.id.swipe_target)
    lateinit var recyclerView: RecyclerView

    private var recyclerAdapter: RecyclerAdapter<IRecyclerCell<*>>? = null

    companion object {
        const val REQUEST_CODE_REFRESH = 1

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

        mPresenter.foundRequest(LoadingStyle.LOADING_PAGE, REQUEST_CODE_REFRESH)
    }

    private fun initSwipeToLoadLayout() {
        swipeToLoadLayout.setOnRefreshListener { mPresenter.foundRequest(LoadingStyle.LOADING_REFRESH, REQUEST_CODE_REFRESH) }
    }

    private fun initRecyclerView() {
        recyclerAdapter = RecyclerAdapter()
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = recyclerAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(context!!, LinearLayoutManager.VERTICAL))
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
        if (pageStatus == RPageStatus.ERROR && viewId == R.id.tv_error)
            mPresenter.foundRequest(LoadingStyle.LOADING_PAGE, REQUEST_CODE_REFRESH)
    }

    override fun handlerResultOtherStyle(status: Int, loadingStyle: Int, requestCode: Int, `object`: Any?) {
        swipeToLoadLayout.isRefreshing = false
    }

}
