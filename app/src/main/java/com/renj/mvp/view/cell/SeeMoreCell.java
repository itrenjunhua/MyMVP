package com.renj.mvp.view.cell;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.renj.mvp.R;
import com.renj.mvp.view.activity.ClassificationActivity;
import com.renj.utils.res.StringUtils;
import com.renj.view.recyclerview.adapter.BaseRecyclerCell;
import com.renj.view.recyclerview.adapter.RecyclerAdapter;
import com.renj.view.recyclerview.adapter.RecyclerViewHolder;
import com.renj.view.recyclerview.adapter.SimpleMultiItemEntity;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-07-08   13:51
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class SeeMoreCell extends BaseRecyclerCell<SimpleMultiItemEntity<String>> {
    public SeeMoreCell() {
        super(R.layout.cell_see_more);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, SimpleMultiItemEntity<String> itemData) {
        if (!StringUtils.isEmpty(itemData.getData())) {
            holder.setText(R.id.cell_see_more, itemData.getData());
        }
    }

    @Override
    public void onItemClick(@NonNull Context context, @NonNull RecyclerAdapter recyclerAdapter,
                            RecyclerViewHolder holder, @NonNull View itemView, int position, SimpleMultiItemEntity<String> itemData) {
        Intent intent = new Intent(context, ClassificationActivity.class);
        context.startActivity(intent);
    }
}
