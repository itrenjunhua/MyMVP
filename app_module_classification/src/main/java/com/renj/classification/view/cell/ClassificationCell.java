package com.renj.classification.view.cell;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.renj.arouter.ARouterPath;
import com.renj.arouter.ARouterUtils;
import com.renj.classification.R;
import com.renj.classification.mode.bean.response.ClassificationRPB;
import com.renj.view.recyclerview.adapter.RecyclerAdapter;
import com.renj.view.recyclerview.adapter.RecyclerCell;
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
        Bundle bundle = new Bundle();
        bundle.putString("title", itemData.label);
        bundle.putInt("pid", itemData.id);
        ARouterUtils.openActivity(ARouterPath.PATH_ACTIVITY_CLASSIFICATION_LIST, ARouterPath.GROUP_CLASSIFICATION, "data", bundle);
    }
}
