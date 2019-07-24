package com.renj.mvp.view.cell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.renj.arouter.ARouterPath;
import com.renj.arouter.ARouterUtils;
import com.renj.common.bean.WebActivityBundleData;
import com.renj.common.bean.WebActivityType;
import com.renj.common.utils.ImageLoaderUtils;
import com.renj.imageloaderlibrary.config.ImageLoadConfig;
import com.renj.mvp.R;
import com.renj.mvp.mode.bean.data.BannerBean;
import com.renj.view.recyclerview.adapter.RecyclerCell;
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
public class BannerCell extends RecyclerCell<List<BannerBean>> {
    public BannerCell(List<BannerBean> itemData) {
        super(itemData);
    }

    @Override
    public int getRecyclerItemType() {
        return IRecyclerCellType.BANNER_CELL_TYPE;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(context, parent, R.layout.cell_banner);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, final List<BannerBean> itemData) {
        final Banner vpBanner = holder.getView(R.id.banner);
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
                ImageLoadConfig config = new ImageLoadConfig
                        .Builder()
                        .url((String) path)
                        .target(imageView)
                        .loadingImageId(R.mipmap.default_loading)
                        .errorImageId(R.mipmap.default_loading)
                        .build();
                ImageLoaderUtils.getDefaultImageLoaderModule().loadImage(config);
            }
        });
        // 设置点击事件
        vpBanner.setOnBannerListener(position1 -> {
            BannerBean bannerBean = itemData.get(position1);
            WebActivityBundleData bundleData = new WebActivityBundleData(0, bannerBean.id, bannerBean.title, "", bannerBean.url, new ArrayList<>(), WebActivityType.TYPE_BANNER);
            ARouterUtils.openActivity(ARouterPath.PATH_ACTIVITY_WEB, ARouterPath.GROUP_COMMON, "data", bundleData);
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
