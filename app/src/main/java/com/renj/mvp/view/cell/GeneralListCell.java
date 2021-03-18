package com.renj.mvp.view.cell;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.renj.mvp.R;
import com.renj.mvp.mode.bean.data.GeneralListBean;
import com.renj.mvp.view.activity.WebViewActivity;
import com.renj.view.recyclerview.adapter.BaseRecyclerCell;
import com.renj.view.recyclerview.adapter.RecyclerAdapter;
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
public class GeneralListCell extends BaseRecyclerCell<GeneralListBean> {

    public GeneralListCell() {
        super(R.layout.cell_general_list);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, GeneralListBean itemData) {
        holder.setText(R.id.general_cell_tv, itemData.title);
    }

    @Override
    public void onItemClick(@NonNull Context context, @NonNull RecyclerAdapter recyclerAdapter,
                            RecyclerViewHolder holder, @NonNull View itemView, int position, GeneralListBean itemData) {
        Intent intent = new Intent(context, WebViewActivity.class);
        WebViewActivity.BundleData bundleData = new WebViewActivity.BundleData(itemData.pid, itemData.id, itemData.title, itemData.content, itemData.url, itemData.images, WebViewActivity.TYPE_LIST);
        intent.putExtra("data", bundleData);
        context.startActivity(intent);
    }
}
