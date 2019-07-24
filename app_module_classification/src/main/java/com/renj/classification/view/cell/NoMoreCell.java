package com.renj.classification.view.cell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.renj.mvp.R;
import com.renj.view.recyclerview.adapter.RecyclerCell;
import com.renj.view.recyclerview.adapter.RecyclerViewHolder;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-07-01   14:43
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class NoMoreCell extends RecyclerCell {
    public NoMoreCell(Object itemData) {
        super(itemData);
    }

    @Override
    public int getRecyclerItemType() {
        return IRecyclerCellType.NO_MORE_CELL_TYPE;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(context,parent, R.layout.status_view_no_more_item);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, Object itemData) {

    }
}
