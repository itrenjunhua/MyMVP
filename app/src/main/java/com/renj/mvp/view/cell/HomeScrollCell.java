package com.renj.mvp.view.cell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.renj.mvp.mode.bean.HomeListRPB;
import com.renj.mvp.weight.HomeTextSwitcher;
import com.renj.recycler.adapter.RecyclerCell;
import com.renj.recycler.adapter.RecyclerViewHolder;

import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-07-01   16:02
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class HomeScrollCell extends RecyclerCell<List<HomeListRPB.NoticeBean>> {
    public HomeScrollCell(List<HomeListRPB.NoticeBean> itemData) {
        super(itemData);
    }

    @Override
    public int getRecyclerItemType() {
        return IRecyclerCellType.HOME_SCROLL_TYPE;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup parent, int viewType) {
        HomeTextSwitcher homeTextSwitcher = new HomeTextSwitcher(context);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        homeTextSwitcher.setLayoutParams(layoutParams);
        return new RecyclerViewHolder(homeTextSwitcher);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, List<HomeListRPB.NoticeBean> itemData) {
        ((HomeTextSwitcher) holder.itemView).setData(itemData);
    }
}
