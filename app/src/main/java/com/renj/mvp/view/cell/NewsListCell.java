package com.renj.mvp.view.cell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.renj.common.utils.DateUtils;
import com.renj.common.utils.UIUtils;
import com.renj.mvp.R;
import com.renj.mvp.mode.bean.NewsListRPB;
import com.renj.mvp.utils.ImageLoaderUtils;
import com.renj.recycler.adapter.RecyclerAdapter;
import com.renj.recycler.adapter.RecyclerCell;
import com.renj.recycler.adapter.RecyclerViewHolder;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-04-17   14:50
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class NewsListCell extends RecyclerCell<NewsListRPB> {

    public NewsListCell(NewsListRPB itemData) {
        super(itemData);
    }

    @Override
    public int getRecyclerItemType() {
        return IRecyclerCellType.NEWS_LIST_CELL_TYPE;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(context, parent, R.layout.news_fragment_item);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, NewsListRPB itemData) {
        holder.setText(R.id.tv_title, itemData.title);
        holder.setText(R.id.tv_content, itemData.content);
        holder.setText(R.id.tv_src, itemData.src);
        holder.setText(R.id.tv_pdate, DateUtils.formatDateAndTime(itemData.time));
        if (itemData.images.size() > 0) {
            holder.getImageView(R.id.iv_image).setVisibility(View.VISIBLE);
            ImageLoaderUtils.getDefaultImageLoaderModule().loadImage(itemData.images.get(0), holder.getImageView(R.id.iv_image));
        } else {
            holder.getImageView(R.id.iv_image).setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(@NonNull Context context, @NonNull RecyclerAdapter recyclerAdapter, @NonNull View itemView, int position, NewsListRPB itemData) {
        UIUtils.showToastSafe(itemData.title);
    }
}
