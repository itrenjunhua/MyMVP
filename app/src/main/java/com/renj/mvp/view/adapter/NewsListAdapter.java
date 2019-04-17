package com.renj.mvp.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.renj.common.adapter.CustomViewHolder;
import com.renj.common.adapter.SingleTypeAdapter;
import com.renj.mvp.R;
import com.renj.mvp.mode.bean.NewsListRPB;
import com.renj.mvp.utils.ImageLoaderUtils;

import butterknife.BindView;

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
public class NewsListAdapter extends SingleTypeAdapter<NewsListRPB> {

    public NewsListAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsListHolder(context, parent, R.layout.news_fragment_item);
    }

    @Override
    public void setData(CustomViewHolder holder, NewsListRPB itemData, int position) {
        NewsListHolder newsListHolder = (NewsListHolder) holder;
        newsListHolder.tvTitle.setText(itemData.full_title);
        newsListHolder.tvContent.setText(itemData.content);
        newsListHolder.tvSrc.setText(itemData.src);
        newsListHolder.tvPdate.setText(itemData.pdate);

        ImageLoaderUtils.getDefaultImageLoaderModule().loadImage(itemData.img, newsListHolder.ivImage);
    }

    static class NewsListHolder extends CustomViewHolder {
        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_src)
        TextView tvSrc;
        @BindView(R.id.tv_pdate)
        TextView tvPdate;

        public NewsListHolder(@NonNull Context context, @NonNull ViewGroup parent, int layoutId) {
            super(context, parent, layoutId);
        }
    }
}
