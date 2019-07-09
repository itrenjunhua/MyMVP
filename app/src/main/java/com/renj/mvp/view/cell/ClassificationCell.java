package com.renj.mvp.view.cell;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.renj.mvp.R;
import com.renj.mvp.mode.bean.response.ClassificationRPB;
import com.renj.mvp.view.activity.ClassificationListActivity;
import com.renj.recycler.adapter.RecyclerAdapter;
import com.renj.recycler.adapter.RecyclerCell;
import com.renj.recycler.adapter.RecyclerViewHolder;

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
public class ClassificationCell extends RecyclerCell<ClassificationRPB> {
    public ClassificationCell(ClassificationRPB itemData) {
        super(itemData);
    }

    @Override
    public int getRecyclerItemType() {
        return IRecyclerCellType.CLASSIFICATION_TYPE;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(context, parent, R.layout.cell_classification);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, ClassificationRPB itemData) {
        holder.setText(R.id.tv_classification_label, itemData.label);
    }

    @Override
    public void onItemClick(@NonNull Context context, @NonNull RecyclerAdapter recyclerAdapter, @NonNull View itemView, int position, ClassificationRPB itemData) {
        Intent intent = new Intent(context, ClassificationListActivity.class);
        intent.putExtra("title",itemData.label);
        intent.putExtra("pid",itemData.id);
        context.startActivity(intent);
    }
}
