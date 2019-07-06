package com.renj.mvp.view.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.BindView
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.renj.daggersupport.DaggerSupportPresenterFragment
import com.renj.mvp.R
import com.renj.mvp.controller.IMyCSDNController
import com.renj.mvp.mode.bean.HomeListRPB
import com.renj.mvp.presenter.MyCSDNPresenter
import com.renj.mvp.view.cell.CellFactory
import com.renj.mvpbase.view.LoadingStyle
import com.renj.pagestatuscontroller.IRPageStatusController
import com.renj.pagestatuscontroller.annotation.RPageStatus
import com.renj.recycler.adapter.IRecyclerCell
import com.renj.recycler.adapter.RecyclerAdapter
import com.renj.recycler.draw.LinearItemDecoration
import java.util.*

/**
 * ======================================================================
 *
 *
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 *
 *
 * 创建时间：2019-04-15   16:45
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
class MyCSDNFragment : DaggerSupportPresenterFragment<MyCSDNPresenter>(), IMyCSDNController.IHomeView {

    @BindView(R.id.swipe_toLoad_layout)
    lateinit var swipeToLoadLayout: SwipeToLoadLayout
    @BindView(R.id.swipe_target)
    lateinit var recyclerView: RecyclerView
    private var recyclerAdapter: RecyclerAdapter<IRecyclerCell<*>>? = null

    companion object {
        const val REQUEST_CODE = 1
        fun newInstance(): MyCSDNFragment {
            val args = Bundle()
            val fragment = MyCSDNFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.home_fragment
    }

    override fun initData() {
        swipeToLoadLayout.setOnRefreshListener { mPresenter.homeListRequest(LoadingStyle.LOADING_REFRESH, REQUEST_CODE) }

        recyclerAdapter = RecyclerAdapter()
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = recyclerAdapter
        recyclerView.addItemDecoration(LinearItemDecoration(LinearLayoutManager.VERTICAL))

        mPresenter.homeListRequest(LoadingStyle.LOADING_PAGE, REQUEST_CODE)
    }

    /**
     * 处理状态页面的事件
     *
     * @param iRPageStatusController 控制器
     * @param pageStatus             点击事件产生的页面状态
     * @param object                 绑定对象
     * @param view                   点击事件产生的 View
     * @param viewId                 点击事件产生的 View 的 id
     */
    override fun handlerPageLoadException(iRPageStatusController: IRPageStatusController<*>, pageStatus: Int, `object`: Any, view: View, viewId: Int) {
        if (pageStatus == RPageStatus.ERROR && viewId == R.id.tv_error)
            mPresenter.homeListRequest(LoadingStyle.LOADING_PAGE, REQUEST_CODE)
    }

    override fun handlerResultOtherStyle(status: Int, loadingStyle: Int, requestCode: Int, `object`: Any?) {
        swipeToLoadLayout.isRefreshing = false
    }

    override fun homeListRequestSuccess(requestCode: Int, homeListRPB: HomeListRPB) {
        val cells = ArrayList<IRecyclerCell<*>>()
        cells.add(CellFactory.createBannerCell(homeListRPB.data.banner))
        cells.add(CellFactory.createNoticeCell(homeListRPB.data.notice))
        cells.addAll(CellFactory.createGeneralListCell(homeListRPB.data.list))
        recyclerAdapter?.setData(cells)
    }
}
