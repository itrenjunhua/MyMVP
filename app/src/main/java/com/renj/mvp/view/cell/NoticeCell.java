package com.renj.mvp.view.cell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.renj.mvp.mode.bean.BannerAndNoticeRPB;
import com.renj.mvp.weight.NoticeTextSwitcher;
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
public class NoticeCell extends RecyclerCell<List<BannerAndNoticeRPB.NoticesEntity>> {
    public NoticeCell(List<BannerAndNoticeRPB.NoticesEntity> itemData) {
        super(itemData);
    }

    @Override
    public int getRecyclerItemType() {
        return IRecyclerCellType.NOTICE_CELL_TYPE;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup parent, int viewType) {
        NoticeTextSwitcher noticeTextSwitcher = new NoticeTextSwitcher(context);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        noticeTextSwitcher.setLayoutParams(layoutParams);
        return new RecyclerViewHolder(noticeTextSwitcher);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, List<BannerAndNoticeRPB.NoticesEntity> itemData) {
        ((NoticeTextSwitcher) holder.itemView).setData(itemData);
    }
}
