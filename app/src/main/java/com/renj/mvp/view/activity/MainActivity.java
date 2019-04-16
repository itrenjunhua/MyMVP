package com.renj.mvp.view.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.renj.common.weight.MyViewPager;
import com.renj.daggersupport.DaggerSupportActivity;
import com.renj.mvp.R;
import com.renj.mvp.view.fragment.HomeFragment;
import com.renj.mvp.view.fragment.MyFragment;
import com.renj.mvp.view.fragment.NewsFragment;
import com.renj.mvpbase.view.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends DaggerSupportActivity {
    @BindView(R.id.view_pager)
    MyViewPager viewPager;
    @BindView(R.id.bottom_tab)
    BottomNavigationBar bottomTab;

    private List<BaseFragment> fragments = new ArrayList<>();

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
                .addItem(new BottomNavigationItem(R.mipmap.new1_s, "实时资讯")
                        .setInactiveIconResource(R.mipmap.new1_n)
                        .setActiveColor(0xFFFF5521)
                        .setInActiveColor(0xFF333333))
                .addItem(new BottomNavigationItem(R.mipmap.new2_s, "新闻检索")
                        .setInactiveIconResource(R.mipmap.new2_n)
                        .setActiveColor(0xFFFF5521)
                        .setInActiveColor(0xFF333333))
                .addItem(new BottomNavigationItem(R.mipmap.my_s, "我的")
                        .setInactiveIconResource(R.mipmap.my_n)
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
