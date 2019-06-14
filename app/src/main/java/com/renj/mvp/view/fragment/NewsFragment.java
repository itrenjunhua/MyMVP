package com.renj.mvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.renj.common.weight.ClearAbleEditText;
import com.renj.daggersupport.DaggerSupportPresenterFragment;
import com.renj.mvp.R;
import com.renj.mvp.controller.INewsController;
import com.renj.mvp.mode.bean.NewsListRPB;
import com.renj.mvp.presenter.NewsPresenter;
import com.renj.mvp.view.cell.CellFactory;
import com.renj.mvp.view.cell.NewsListCell;
import com.renj.recycler.adapter.RecyclerAdapter;

import butterknife.BindView;
import butterknife.OnClick;

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
    private final int REQUEST_CODE_REFRESH = 0;
    private final int REQUEST_CODE_SEARCH = 1;

    @BindView(R.id.et_search)
    ClearAbleEditText etSearch;
    @BindView(R.id.recycler_view)
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
        requestListData(REQUEST_CODE_REFRESH);
    }

    private void initRecyclerView() {
        recyclerAdapter = new RecyclerAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
    }

    @OnClick(R.id.tv_search)
    public void clickView() {
        requestListData(REQUEST_CODE_SEARCH);
    }

    private void requestListData(int requestCode) {
        mPresenter.newsListRequest(requestCode, etSearch.getText().toString().trim());
    }

    @Override
    public void newsListRequestSuccess(int requestCode, @NonNull NewsListRPB newsListRPB) {
        recyclerAdapter.setData(CellFactory.createNewsListCell(newsListRPB.result));
    }
}
