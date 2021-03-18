package com.renj.mvp.view.cell;

import android.support.annotation.NonNull;

import com.renj.mvp.R;
import com.renj.mvp.mode.bean.data.NoticeBean;
import com.renj.mvp.weight.NoticeTextSwitcher;
import com.renj.view.recyclerview.adapter.BaseRecyclerCell;
import com.renj.view.recyclerview.adapter.RecyclerViewHolder;

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
public class NoticeCell extends BaseRecyclerCell<List<NoticeBean>> {
    public NoticeCell() {
        super(R.layout.cell_notice);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, List<NoticeBean> itemData) {
        ((NoticeTextSwitcher) holder.itemView).setData(itemData);
    }
}
