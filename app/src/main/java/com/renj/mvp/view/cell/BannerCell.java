package com.renj.mvp.view.cell;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.renj.mvp.R;
import com.renj.mvp.mode.bean.data.BannerBean;
import com.renj.mvp.utils.ImageLoaderUtils;
import com.renj.mvp.view.activity.WebViewActivity;
import com.renj.view.recyclerview.adapter.BaseRecyclerCell;
import com.renj.view.recyclerview.adapter.RecyclerViewHolder;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-07-01   16:30
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class BannerCell extends BaseRecyclerCell<List<BannerBean>> {
    public BannerCell() {
        super(R.layout.cell_banner);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, List<BannerBean> itemData) {
        Banner vpBanner = (Banner) holder.getView(R.id.banner);
        List<String> images = new ArrayList<>();
        List<String> titles = new ArrayList<>();

        for (BannerBean itemDatum : itemData) {
            images.add(itemDatum.image);
            titles.add(itemDatum.title);
        }

        //设置banner样式
        vpBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        vpBanner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                ImageLoaderUtils.loadImage(context, (String) path, imageView);
            }
        });
        // 设置点击事件
        vpBanner.setOnBannerListener(position1 -> {
            Intent intent = new Intent(holder.itemView.getContext(), WebViewActivity.class);
            BannerBean bannerBean = itemData.get(position1);
            WebViewActivity.BundleData bundleData = new WebViewActivity.BundleData(0, bannerBean.id, bannerBean.title, "", bannerBean.url, new ArrayList<>(), WebViewActivity.TYPE_BANNER);
            intent.putExtra("data", bundleData);
            holder.itemView.getContext().startActivity(intent);
        });
        //设置图片集合
        vpBanner.setImages(images);
        //设置banner动画效果
        vpBanner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        vpBanner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        vpBanner.isAutoPlay(true);
        //设置轮播时间
        vpBanner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        vpBanner.setIndicatorGravity(BannerConfig.RIGHT);
        //banner设置方法全部调用完毕时最后调用
        vpBanner.start();
    }
}
