package com.renj.mvp.view.fragment

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.BindView
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.renj.daggersupport.DaggerSupportPresenterFragment
import com.renj.mvp.R
import com.renj.mvp.controller.INewsController
import com.renj.mvp.mode.bean.NewsListRPB
import com.renj.mvp.presenter.NewsPresenter
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
class NewsFragment : DaggerSupportPresenterFragment<NewsPresenter>(), INewsController.INewsView {

    @BindView(R.id.swipe_toLoad_layout)
    lateinit var swipeToLoadLayout: SwipeToLoadLayout
    @BindView(R.id.swipe_target)
    lateinit var recyclerView: RecyclerView

    private var recyclerAdapter: RecyclerAdapter<IRecyclerCell<*>>? = null

    // 记录加载次数，模拟没有更多数据
    private var loadCount = 0

    companion object {
        const val REQUEST_CODE_REFRESH = 1
        const val REQUEST_CODE_LOAD = 2

        fun newInstance(): NewsFragment {
            val args = Bundle()
            val fragment = NewsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.news_fragment
    }

    override fun initData() {
        initSwipeToLoadLayout()
        initRecyclerView()
        requestListData(REQUEST_CODE_REFRESH, LoadingStyle.LOADING_PAGE)
    }

    private fun initSwipeToLoadLayout() {
        swipeToLoadLayout.setOnRefreshListener { requestListData(REQUEST_CODE_REFRESH, LoadingStyle.LOADING_REFRESH) }

        swipeToLoadLayout.setOnLoadMoreListener { requestListData(REQUEST_CODE_LOAD, LoadingStyle.LOADING_LOAD_MORE) }
    }

    private fun initRecyclerView() {
        recyclerAdapter = RecyclerAdapter()
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = recyclerAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(context!!, LinearLayoutManager.VERTICAL))
    }

    private fun requestListData(requestCode: Int, @LoadingStyle loadingStyle: Int) {
        if (requestCode == REQUEST_CODE_REFRESH)
            loadCount = 0
        else
            loadCount += 1
        mPresenter.newsListRequest(loadingStyle, requestCode, 10)
    }

    override fun newsListRequestSuccess(requestCode: Int, newsListRPB: NewsListRPB) {
        if (requestCode == REQUEST_CODE_REFRESH)
            recyclerAdapter!!.setData(CellFactory.createNewsListCell(newsListRPB.data) as List<IRecyclerCell<*>>)
        else if (requestCode == REQUEST_CODE_LOAD)
            recyclerAdapter!!.addAndNotifyItem(CellFactory.createNewsListCell(newsListRPB.data) as List<IRecyclerCell<*>>)

        if (isNoMore())
            recyclerAdapter!!.addAndNotifyItem(CellFactory.createNoMoreCell())
    }

    override fun handlerPageLoadException(iRPageStatusController: IRPageStatusController<*>, pageStatus: Int, `object`: Any, view: View, viewId: Int) {
        if (pageStatus == RPageStatus.ERROR && viewId == R.id.tv_error)
            requestListData(REQUEST_CODE_REFRESH, LoadingStyle.LOADING_PAGE)
    }

    override fun handlerResultOtherStyle(status: Int, loadingStyle: Int, requestCode: Int, `object`: Any?) {
        if (requestCode == REQUEST_CODE_REFRESH)
            swipeToLoadLayout.isRefreshing = false
        else if (requestCode == REQUEST_CODE_LOAD)
            swipeToLoadLayout.isLoadingMore = false

        swipeToLoadLayout.isLoadMoreEnabled = !isNoMore()
    }

    private fun isNoMore() = loadCount >= 3

}
