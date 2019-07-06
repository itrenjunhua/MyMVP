package com.renj.mvp.view.cell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.renj.common.utils.UIUtils;
import com.renj.mvp.R;
import com.renj.mvp.mode.bean.HomeListRPB;
import com.renj.recycler.adapter.RecyclerAdapter;
import com.renj.recycler.adapter.RecyclerCell;
import com.renj.recycler.adapter.RecyclerViewHolder;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：itrenjunhua@163.com
 * <p>
 * 创建时间：2019-06-14   14:27
 * <p>
 * 描述：一般的List条目样式
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class GeneralListCell extends RecyclerCell<HomeListRPB.ListBean> {
    public GeneralListCell(HomeListRPB.ListBean itemData) {
        super(itemData);
    }

    @Override
    public int getRecyclerItemType() {
        return IRecyclerCellType.GENERAL_LIST_TYPE;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(context,parent, R.layout.cell_general_list);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, HomeListRPB.ListBean itemData) {
        ((TextView) (holder.getItemView())).setText(itemData.title);
    }

    @Override
    public void onItemClick(@NonNull Context context, @NonNull RecyclerAdapter recyclerAdapter, @NonNull View itemView, int position, HomeListRPB.ListBean itemData) {
        UIUtils.showToastSafe(itemData.title);
    }
}
