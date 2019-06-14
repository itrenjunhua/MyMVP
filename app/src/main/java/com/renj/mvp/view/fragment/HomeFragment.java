package com.renj.mvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.renj.daggersupport.DaggerSupportPresenterFragment;
import com.renj.mvp.R;
import com.renj.mvp.controller.IHomeController;
import com.renj.mvp.mode.bean.HomeListRPB;
import com.renj.mvp.presenter.HomePresenter;
import com.renj.mvp.view.cell.CellFactory;
import com.renj.mvp.view.cell.HomeListCell;
import com.renj.recycler.adapter.RecyclerAdapter;
import com.renj.recycler.draw.LinearItemDecoration;

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
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private RecyclerAdapter<HomeListCell> recyclerAdapter;

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
        recyclerAdapter = new RecyclerAdapter<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.addItemDecoration(new LinearItemDecoration(LinearLayoutManager.VERTICAL));

        mPresenter.homeListRequest(REQUEST_CODE);
    }

    @Override
    public void homeListRequestSuccess(int requestCode, @NonNull HomeListRPB homeListRPB) {
        recyclerAdapter.setData(CellFactory.createHomeListCell(homeListRPB.result));
    }

}
