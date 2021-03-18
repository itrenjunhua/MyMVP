package com.renj.mvp.view.cell;

import android.support.annotation.NonNull;

import com.renj.mvp.R;
import com.renj.view.recyclerview.adapter.BaseRecyclerCell;
import com.renj.view.recyclerview.adapter.RecyclerViewHolder;
import com.renj.view.recyclerview.adapter.SimpleMultiItemEntity;

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
public class SegmentationCell extends BaseRecyclerCell<SimpleMultiItemEntity<String>> {
    public SegmentationCell() {
        super(R.layout.cell_segmentation);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, SimpleMultiItemEntity<String> itemData) {
        holder.setText(R.id.segmentation_name, itemData.getData());
    }
}
