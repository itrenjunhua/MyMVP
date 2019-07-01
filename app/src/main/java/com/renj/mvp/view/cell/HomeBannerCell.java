package com.renj.mvp.view.cell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.renj.common.utils.UIUtils;
import com.renj.mvp.R;
import com.renj.mvp.mode.bean.HomeListRPB;
import com.renj.mvp.utils.ImageLoaderUtils;
import com.renj.recycler.adapter.RecyclerCell;
import com.renj.recycler.adapter.RecyclerViewHolder;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
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
public class HomeBannerCell extends RecyclerCell<List<HomeListRPB.BannerBean>> {
    public HomeBannerCell(List<HomeListRPB.BannerBean> itemData) {
        super(itemData);
    }

    @Override
    public int getRecyclerItemType() {
        return IRecyclerCellType.HOME_BANNER_TYPE;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(context, parent, R.layout.home_fragment_banner_item);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, final List<HomeListRPB.BannerBean> itemData) {
        final Banner vpBanner = holder.getView(R.id.banner);
        List<String> images = new ArrayList<>();
        List<String> titles = new ArrayList<>();

        String url = "http://pic1.win4000.com/wallpaper/c/53cdd1f7c1f21.jpg";
        for (HomeListRPB.BannerBean itemDatum : itemData) {
            //images.add(itemDatum.image);
            images.add(url);
            titles.add(itemDatum.title);
        }

        //设置banner样式
        vpBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        vpBanner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                ImageLoaderUtils.getDefaultImageLoaderModule().loadImage((String) path, imageView);
            }
        });
        // 设置点击事件
        vpBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                UIUtils.showToastSafe(itemData.get(position).clickResult);
            }
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
