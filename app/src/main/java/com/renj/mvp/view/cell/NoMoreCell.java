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
 * 创建时间：2019-07-01   14:43
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class NoMoreCell extends BaseRecyclerCell<SimpleMultiItemEntity<Object>> {
    public NoMoreCell() {
        super(R.layout.status_view_no_more_item);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, SimpleMultiItemEntity<Object> itemData) {

    }
}
