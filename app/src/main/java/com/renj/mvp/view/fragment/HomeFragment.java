package com.renj.mvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.renj.daggersupport.DaggerSupportPresenterFragment;
import com.renj.mvp.R;
import com.renj.mvp.controller.IHomeController;
import com.renj.mvp.mode.bean.HomeListRPB;
import com.renj.mvp.presenter.HomePresenter;
import com.renj.mvp.view.cell.CellFactory;
import com.renj.mvpbase.view.LoadingStyle;
import com.renj.pagestatuscontroller.IRPageStatusController;
import com.renj.pagestatuscontroller.annotation.RPageStatus;
import com.renj.recycler.adapter.RecyclerAdapter;
import com.renj.recycler.draw.LinearItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-04-15   16:45
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class HomeFragment extends DaggerSupportPresenterFragment<HomePresenter>
        implements IHomeController.IHomeView {

    private final int REQUEST_CODE = 1;
    @BindView(R.id.swipe_toLoad_layout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.swipe_target)
    RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.home_fragment;
    }

    @Override
    public void initData() {
        swipeToLoadLayout.setOnRefreshListener(() -> {
            mPresenter.homeListRequest(LoadingStyle.LOADING_REFRESH, REQUEST_CODE);
        });

        recyclerAdapter = new RecyclerAdapter<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.addItemDecoration(new LinearItemDecoration(LinearLayoutManager.VERTICAL));

        mPresenter.homeListRequest(LoadingStyle.LOADING_PAGE, REQUEST_CODE);
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
    @Override
    protected void handlerPageLoadException(IRPageStatusController iRPageStatusController, int pageStatus, Object object, View view, int viewId) {
        if (pageStatus == RPageStatus.ERROR && viewId == R.id.tv_error)
            mPresenter.homeListRequest(LoadingStyle.LOADING_PAGE, REQUEST_CODE);
    }

    @Override
    protected void handlerResultOtherStyle(int status, int loadingStyle, int requestCode, @Nullable Object object) {
        swipeToLoadLayout.setRefreshing(false);
    }

    @Override
    public void homeListRequestSuccess(int requestCode, @NonNull HomeListRPB homeListRPB) {
        List cells = new ArrayList();
        cells.add(CellFactory.createHomeBannerCell(homeListRPB.data.banner));
        cells.add(CellFactory.createHomeScrollCell(homeListRPB.data.notice));
        cells.addAll(CellFactory.createHomeListCell(homeListRPB.data.list));
        recyclerAdapter.setData(cells);
    }
}
