package com.renj.mvp.view.cell;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.renj.mvp.R;
import com.renj.mvp.mode.bean.HomeListRPB;
import com.renj.recycler.adapter.RecyclerCell;
import com.renj.recycler.adapter.RecyclerViewHolder;

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
        final ViewPager vpBanner = holder.getView(R.id.vp_home_banner);
        final TextView tvBanner = holder.getTextView(R.id.tv_home_banner);

        vpBanner.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                tvBanner.setText(itemData.get(position).title);
            }
        });

        vpBanner.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return itemData.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return false;
            }

            @NonNull
            @Override
            public View instantiateItem(@NonNull ViewGroup container, int position) {
                ImageView imageView = new ImageView(vpBanner.getContext());
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                imageView.setBackgroundColor(Color.parseColor("#ff0000"));
                imageView.setImageResource(R.mipmap.ic_launcher);
                container.addView(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        });

        vpBanner.setCurrentItem(0);
    }
}
