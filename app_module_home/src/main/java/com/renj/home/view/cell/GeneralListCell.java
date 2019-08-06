package com.renj.home.view.cell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.renj.common.utils.aroute.ARouterPath;
import com.renj.common.utils.aroute.ARouterUtils;
import com.renj.common.mode.bean.bundle.WebActivityBundleData;
import com.renj.common.mode.bean.bundle.WebActivityType;
import com.renj.common.mode.db.GeneralListData;
import com.renj.home.R;
import com.renj.view.recyclerview.adapter.RecyclerAdapter;
import com.renj.view.recyclerview.adapter.RecyclerCell;
import com.renj.view.recyclerview.adapter.RecyclerViewHolder;

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
public class GeneralListCell extends RecyclerCell<GeneralListData> {
    public GeneralListCell(GeneralListData itemData) {
        super(itemData);
    }

    @Override
    public int getRecyclerItemType() {
        return IRecyclerCellType.GENERAL_LIST_TYPE;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(context, parent, R.layout.cell_general_list);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, GeneralListData itemData) {
        holder.setText(R.id.general_cell_tv, itemData.title);
    }

    @Override
    public void onItemClick(@NonNull Context context, @NonNull RecyclerAdapter recyclerAdapter,
                            @NonNull RecyclerViewHolder holder, @NonNull View itemView, int position, GeneralListData itemData) {
        WebActivityBundleData bundleData = new WebActivityBundleData(itemData.pid, itemData.id, itemData.title, itemData.content, itemData.url, itemData.images, WebActivityType.TYPE_LIST);
        ARouterUtils.openActivity(ARouterPath.PATH_COMMON_ACTIVITY_WEB,"data", bundleData);
    }
}
