package com.renj.mvp.view.activity

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.renj.mvp.R
import com.renj.mvp.controller.IClassificationListController
import com.renj.mvp.mode.bean.response.GeneralListRPB
import com.renj.mvp.presenter.ClassificationListPresenter
import com.renj.mvp.utils.MyCommonUtils
import com.renj.mvp.view.cell.*
import com.renj.mvpbase.view.LoadingStyle
import com.renj.pagestatuscontroller.IRPageStatusController
import com.renj.pagestatuscontroller.annotation.RPageStatus
import com.renj.rxsupport.rxview.RxBasePresenterActivity
import com.renj.utils.res.StringUtils
import com.renj.view.recyclerview.adapter.*
import com.renj.view.recyclerview.draw.LinearItemDecoration
import kotlinx.android.synthetic.main.classification_list_activity.*

/**
 * ======================================================================
 *
 *
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 *
 *
 * 创建时间：2019-07-09   11:03
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
class ClassificationListActivity : RxBasePresenterActivity<ClassificationListPresenter>(), IClassificationListController.IClassificationListView {
    private var pageNo = 1
    private var pageSize = 20

    private var pid: Int = 0
    private var recyclerAdapter: MultiItemAdapter<MultiItemEntity>? = null

    override fun getLayoutId(): Int {
        return R.layout.classification_list_activity
    }

    override fun initView(contentView: View?) {
    }

    override fun initData() {
        setPageTitle(StringUtils.handlerString(intent.getStringExtra("title")))

        pid = intent.getIntExtra("pid", 0)

        swipe_toLoad_layout.setOnRefreshListener {
            pageNo = 1
            mPresenter.classificationListRequest(LoadingStyle.LOADING_REFRESH, pid, pageNo, pageSize)
        }
        swipe_toLoad_layout.setOnLoadMoreListener {
            mPresenter.classificationListRequest(LoadingStyle.LOADING_LOAD_MORE, pid, pageNo, pageSize)
        }

        recyclerAdapter = object : MultiItemAdapter<MultiItemEntity>() {
            override fun <C : BaseRecyclerCell<MultiItemEntity?>?> getRecyclerCell(itemTypeValue: Int): C {
                return when (itemTypeValue) {
                    RecyclerItemType.NO_MORE_CELL_TYPE -> {
                        NoMoreCell() as C
                    }
                    else ->
                        GeneralListCell() as C
                }
            }
        }
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        swipe_target.layoutManager = linearLayoutManager
        swipe_target.adapter = recyclerAdapter
        swipe_target.addItemDecoration(LinearItemDecoration(LinearLayoutManager.VERTICAL))

        pageNo = 1
        mPresenter.classificationListRequest(LoadingStyle.LOADING_PAGE, pid, pageNo, pageSize)
    }

    override fun classificationListRequestSuccess(loadingStyle: Int, generalListRPB: GeneralListRPB) {
        if (loadingStyle == LoadingStyle.LOADING_REFRESH || loadingStyle == LoadingStyle.LOADING_PAGE)
            recyclerAdapter?.setData(generalListRPB.data.list as List<MultiItemEntity>)
        if (loadingStyle == LoadingStyle.LOADING_LOAD_MORE)
            recyclerAdapter?.addAndNotifyAll(generalListRPB.data.list as List<MultiItemEntity>)

        if (pageNo >= generalListRPB.data.page) {
            swipe_toLoad_layout.isLoadingMore = false
            swipe_toLoad_layout.isLoadMoreEnabled = false
            recyclerAdapter?.addAndNotifyAll(SimpleMultiItemEntity(RecyclerItemType.NO_MORE_CELL_TYPE, null))
        } else {
            swipe_toLoad_layout.isLoadMoreEnabled = true
        }
        pageNo += 1
    }

    override fun handlerPageLoadException(iRPageStatusController: IRPageStatusController<out IRPageStatusController<*>>?, pageStatus: Int, `object`: Any?, view: View?, viewId: Int) {
        if (pageStatus == RPageStatus.ERROR && viewId == R.id.tv_error) {
            pageNo = 1
            mPresenter.classificationListRequest(LoadingStyle.LOADING_PAGE, pid, pageNo, pageSize)
        } else if (pageStatus == RPageStatus.NET_WORK && viewId == R.id.tv_reload) {
            pageNo = 1
            // 此处修改页面状态是因为在 MyApplication 中指定了当网络异常时点击不自动修改为 loading 状态
            rPageStatusController.changePageStatus(RPageStatus.LOADING)
            mPresenter.classificationListRequest(LoadingStyle.LOADING_PAGE, pid, pageNo, pageSize)
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
