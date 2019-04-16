package com.renj.mvp.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.renj.common.adapter.BaseListAdapter;
import com.renj.common.utils.UIUtils;
import com.renj.daggersupport.DaggerSupportPresenterFragment;
import com.renj.mvp.R;
import com.renj.mvp.controller.IHomeController;
import com.renj.mvp.mode.bean.HomeListRPB;
import com.renj.mvp.presenter.HomePresenter;

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
    @BindView(R.id.list_view)
    ListView listView;
    private List<String> resultList;

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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (resultList != null) {
                    UIUtils.showToastSafe(resultList.get(position));
                }
            }
        });

        mPresenter.homeListRequest(REQUEST_CODE);
    }

    @Override
    public void homeListRequestSuccess(int requestCode, @NonNull HomeListRPB homeListRPB) {
        resultList = homeListRPB.result;
        listView.setAdapter(new BaseListAdapter<String>(this, resultList) {
            @Override
            protected BaseListViewHolder getViewHolder(Context context, ViewGroup parent, int position) {
                return new BaseListViewHolder<String>(context, parent) {
                    @Override
                    public int getItemViewLayoutId() {
                        return R.layout.home_fragment_item;
                    }

                    @Override
                    public void setData(int position, String data) {
                        ((TextView) (mItemRootView)).setText(data);
                    }
                };
            }
        });
    }

}
