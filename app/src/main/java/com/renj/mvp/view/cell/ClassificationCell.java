package com.renj.mvp.view.cell;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.renj.mvp.R;
import com.renj.mvp.mode.bean.response.ClassificationRPB;
import com.renj.mvp.view.activity.ClassificationListActivity;
import com.renj.view.recyclerview.adapter.BaseRecyclerCell;
import com.renj.view.recyclerview.adapter.RecyclerAdapter;
import com.renj.view.recyclerview.adapter.RecyclerViewHolder;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-07-08   17:16
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ClassificationCell extends BaseRecyclerCell<ClassificationRPB> {
    public ClassificationCell() {
        super(R.layout.cell_classification);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, ClassificationRPB itemData) {
        holder.setText(R.id.tv_classification_label, itemData.label);
    }

    @Override
    public void onItemClick(@NonNull Context context, @NonNull RecyclerAdapter recyclerAdapter,
                            RecyclerViewHolder holder, @NonNull View itemView, int position, ClassificationRPB itemData) {
        Intent intent = new Intent(context, ClassificationListActivity.class);
        intent.putExtra("title", itemData.label);
        intent.putExtra("pid", itemData.id);
        context.startActivity(intent);
    }
}
