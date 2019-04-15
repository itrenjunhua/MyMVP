package com.renj.mvp.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.renj.common.weight.MyViewPager;
import com.renj.daggersupport.DaggerSupportActivity;
import com.renj.daggersupport.DaggerSupportFragment;
import com.renj.mvp.R;
import com.renj.mvp.view.fragment.HomeFragment;
import com.renj.mvp.view.fragment.MyFragment;
import com.renj.mvp.view.fragment.NewsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends DaggerSupportActivity {
    @BindView(R.id.view_pager)
    MyViewPager viewPager;
    @BindView(R.id.bottom_tab)
    BottomNavigationBar bottomTab;

    private List<DaggerSupportFragment> fragments = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        initFragments();
        initViewPager();
        initNavigationBar();
    }

    private void initFragments() {
        fragments.add(HomeFragment.newInstance());
        fragments.add(NewsFragment.newInstance());
        fragments.add(MyFragment.newInstance());
    }

    private void initViewPager() {
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
    }

    private void initNavigationBar() {
        bottomTab
                .setMode(BottomNavigationBar.MODE_FIXED)
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "首页")
                        .setInactiveIconResource(R.mipmap.ic_launcher)
                        .setActiveColor(0xFFFF5521)
                        .setInActiveColor(0xFF333333))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "资讯")
                        .setInactiveIconResource(R.mipmap.ic_launcher)
                        .setActiveColor(0xFFFF5521)
                        .setInActiveColor(0xFF333333))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "我的")
                        .setInactiveIconResource(R.mipmap.ic_launcher)
                        .setActiveColor(0xFFFF5521)
                        .setInActiveColor(0xFF333333))
                .initialise();

        bottomTab.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                viewPager.setCurrentItem(position, false);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

}
