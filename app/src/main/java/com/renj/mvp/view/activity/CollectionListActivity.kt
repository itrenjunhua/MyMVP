package com.renj.mvp.view.activity

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.renj.mvp.R
import com.renj.mvp.controller.ICollectionListController
import com.renj.mvp.mode.db.bean.ListSeeAndCollectionRDB
import com.renj.mvp.presenter.CollectionListPresenter
import com.renj.mvp.utils.MyCommonUtils
import com.renj.mvp.view.cell.CellFactory
import com.renj.mvpbase.view.LoadingStyle
import com.renj.pagestatuscontroller.IRPageStatusController
import com.renj.pagestatuscontroller.annotation.RPageStatus
import com.renj.rxsupport.rxview.RxBasePresenterActivity
import com.renj.view.recyclerview.adapter.IRecyclerCell
import com.renj.view.recyclerview.adapter.RecyclerAdapter
import com.renj.view.recyclerview.draw.LinearItemDecoration
import kotlinx.android.synthetic.main.see_and_collection_list_activity.*

/**
 * ======================================================================
 *
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 *
 * 创建时间：2019-07-11   14:01
 *
 * 描述：
 *
 * 修订历史：
 *
 * ======================================================================
 */
class CollectionListActivity : RxBasePresenterActivity<CollectionListPresenter>(), ICollectionListController.ICollectionListView {

    private var pageNo = 1
    private var pageSize = 20
    private var recyclerAdapter: RecyclerAdapter<IRecyclerCell<*>>? = null

    override fun getLayoutId(): Int {
        return R.layout.see_and_collection_list_activity
    }

    override fun initView() {
    }

    override fun initData() {
        setPageBack(true, false, null)
        setPageTitle(R.string.me_collection)
        swipe_toLoad_layout.setOnRefreshListener {
            pageNo = 1
            mPresenter.listResponse(LoadingStyle.LOADING_REFRESH, pageNo, pageSize)
        }
        swipe_toLoad_layout.setOnLoadMoreListener {
            mPresenter.listResponse(LoadingStyle.LOADING_LOAD_MORE, pageNo, pageSize)
        }

        recyclerAdapter = RecyclerAdapter()
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        swipe_target.layoutManager = linearLayoutManager
        swipe_target.adapter = recyclerAdapter
        swipe_target.addItemDecoration(LinearItemDecoration(LinearLayoutManager.VERTICAL))

        pageNo = 1
        mPresenter.listResponse(LoadingStyle.LOADING_PAGE, pageNo, pageSize)
    }

    override fun listResponseSuccess(loadingStyle: Int, collectionRDB: ListSeeAndCollectionRDB) {
        if (loadingStyle == LoadingStyle.LOADING_REFRESH || loadingStyle == LoadingStyle.LOADING_PAGE)
            recyclerAdapter?.setData(CellFactory.createSeeAndCollectionListCell(collectionRDB.list, false) as List<IRecyclerCell<*>>)
        else
            recyclerAdapter?.addAndNotifyAll(CellFactory.createSeeAndCollectionListCell(collectionRDB.list, false) as List<IRecyclerCell<*>>)

        if (pageNo >= collectionRDB.page) {
            swipe_toLoad_layout.isLoadingMore = false
            swipe_toLoad_layout.isLoadMoreEnabled = false
            recyclerAdapter?.addAndNotifyAll(CellFactory.createNoMoreCell() as IRecyclerCell<*>)
        } else {
            swipe_toLoad_layout.isLoadMoreEnabled = true
        }
        pageNo += 1
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
        if (pageStatus == RPageStatus.ERROR && viewId == R.id.tv_error) {
            pageNo = 1
            mPresenter.listResponse(LoadingStyle.LOADING_PAGE, pageNo, pageSize)
        } else if (pageStatus == RPageStatus.NET_WORK && viewId == R.id.tv_reload) {
            pageNo = 1
            // 此处修改页面状态是因为在 MyApplication 中指定了当网络异常时点击不自动修改为 loading 状态
            rPageStatusController.changePageStatus(RPageStatus.LOADING)
            mPresenter.listResponse(LoadingStyle.LOADING_PAGE, pageNo, pageSize)
        } else if (pageStatus == RPageStatus.NET_WORK && viewId == R.id.tv_net_work) {
            MyCommonUtils.openNetWorkActivity(this)
        }

    }

    override fun showCustomResultPage(status: Int, loadingStyle: Int, `object`: Any?) {
        if (loadingStyle == LoadingStyle.LOADING_REFRESH)
            swipe_toLoad_layout.isRefreshing = false
        if (loadingStyle == LoadingStyle.LOADING_LOAD_MORE)
            swipe_toLoad_layout.isLoadingMore = false
    }
}