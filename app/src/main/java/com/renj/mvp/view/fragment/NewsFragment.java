package com.renj.mvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.renj.common.weight.ClearAbleEditText;
import com.renj.daggersupport.DaggerSupportPresenterFragment;
import com.renj.mvp.R;
import com.renj.mvp.controller.INewsController;
import com.renj.mvp.mode.bean.NewsListRPB;
import com.renj.mvp.presenter.NewsPresenter;
import com.renj.mvp.view.cell.NewsListCell;
import com.renj.mvpbase.view.LoadingStyle;
import com.renj.pagestatuscontroller.IRPageStatusController;
import com.renj.pagestatuscontroller.RPageStatusController;
import com.renj.pagestatuscontroller.annotation.RPageStatus;
import com.renj.pagestatuscontroller.listener.OnRPageEventListener;
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
    // 不需要将状态改变绑定到 Fragment，只需要绑定到 View 上
    private RPageStatusController rPageStatusController;

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
        rPageStatusController = RPageStatusController.get();
        rPageStatusController.resetOnRPageEventListener(RPageStatus.ERROR, new OnRPageEventListener() {
            @Override
            public void onViewClick(@NonNull IRPageStatusController iRPageStatusController, int pageStatus, @NonNull Object object, @NonNull View view, int viewId) {
                Log.i("NewsFragment", "iRPageStatusController = [" + iRPageStatusController + "], pageStatus = [" + pageStatus + "], object = [" + object + "], view = [" + view + "], viewId = [" + viewId + "]");
                requestListData(REQUEST_CODE_REFRESH, LoadingStyle.LOADING_PAGE);
            }
        });
        rPageStatusController.bind(recyclerView);
        replaceSupperPageStatusController(rPageStatusController);

        initRecyclerView();
        requestListData(REQUEST_CODE_REFRESH, LoadingStyle.LOADING_PAGE);
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
        requestListData(REQUEST_CODE_SEARCH, LoadingStyle.LOADING_DIALOG);
    }

    private void requestListData(int loadingStyle, int requestCode) {
        //mPresenter.newsListRequest(loadingStyle, requestCode, etSearch.getText().toString().trim());
    }

    @Override
    public void newsListRequestSuccess(int requestCode, @NonNull NewsListRPB newsListRPB) {
        //recyclerAdapter.setData(CellFactory.createNewsListCell(newsListRPB.result));
    }
}
