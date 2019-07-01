package com.renj.mvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.renj.daggersupport.DaggerSupportPresenterFragment;
import com.renj.mvp.R;
import com.renj.mvp.controller.INewsController;
import com.renj.mvp.mode.bean.NewsListRPB;
import com.renj.mvp.presenter.NewsPresenter;
import com.renj.mvp.view.cell.CellFactory;
import com.renj.mvp.view.cell.NewsListCell;
import com.renj.mvpbase.view.LoadingStyle;
import com.renj.pagestatuscontroller.IRPageStatusController;
import com.renj.pagestatuscontroller.annotation.RPageStatus;
import com.renj.recycler.adapter.RecyclerAdapter;

import butterknife.BindView;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-04-15   16:57
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class NewsFragment extends DaggerSupportPresenterFragment<NewsPresenter>
        implements INewsController.INewsView {
    private final int REQUEST_CODE_REFRESH = 1;
    private final int REQUEST_CODE_LOAD = 2;

    @BindView(R.id.swipe_toLoad_layout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.swipe_target)
    RecyclerView recyclerView;
    private RecyclerAdapter<NewsListCell> recyclerAdapter;

    public static NewsFragment newInstance() {
        Bundle args = new Bundle();
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.news_fragment;
    }

    @Override
    public void initData() {
        initRecyclerView();
        initSwipeToLoadLayout();
        requestListData(REQUEST_CODE_REFRESH, LoadingStyle.LOADING_PAGE);
    }

    private void initSwipeToLoadLayout() {
        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestListData(REQUEST_CODE_REFRESH, LoadingStyle.LOADING_REFRESH);
            }
        });

        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                requestListData(REQUEST_CODE_LOAD, LoadingStyle.LOADING_LOAD_MORE);
            }
        });
    }

    private void initRecyclerView() {
        recyclerAdapter = new RecyclerAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
    }

    private void requestListData(int requestCode, int loadingStyle) {
        mPresenter.newsListRequest(loadingStyle, requestCode, 10);
    }

    @Override
    public void newsListRequestSuccess(int requestCode, @NonNull NewsListRPB newsListRPB) {
        if (requestCode == REQUEST_CODE_REFRESH)
            recyclerAdapter.setData(CellFactory.createNewsListCell(newsListRPB.data));
        else if (requestCode == REQUEST_CODE_LOAD)
            recyclerAdapter.addAndNotifyItem(CellFactory.createNewsListCell(newsListRPB.data));
    }

    @Override
    protected void handlerLoadException(IRPageStatusController iRPageStatusController, int pageStatus, Object object, View view, int viewId) {
        if (pageStatus == RPageStatus.ERROR && viewId == R.id.tv_error)
            requestListData(REQUEST_CODE_REFRESH, LoadingStyle.LOADING_PAGE);
    }

    @Override
    protected void handlerOtherStyle(int status, int requestCode, @Nullable Object object) {
        if (requestCode == REQUEST_CODE_REFRESH)
            swipeToLoadLayout.setRefreshing(false);
        else if (requestCode == REQUEST_CODE_LOAD)
            swipeToLoadLayout.setLoadingMore(false);
    }
}
