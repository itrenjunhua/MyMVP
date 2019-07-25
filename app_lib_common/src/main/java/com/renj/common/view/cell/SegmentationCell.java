package com.renj.common.view.cell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.renj.common.R;
import com.renj.view.recyclerview.adapter.RecyclerCell;
import com.renj.view.recyclerview.adapter.RecyclerViewHolder;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-07-08   10:27
 * <p>
 * 描述：推荐分割样式
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class SegmentationCell extends RecyclerCell<String> {
    public SegmentationCell(String itemData) {
        super(itemData);
    }

    @Override
    public int getRecyclerItemType() {
        return 65534;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(context, parent, R.layout.cell_segmentation);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, String itemData) {
        holder.setText(R.id.segmentation_name, itemData);
    }
}
