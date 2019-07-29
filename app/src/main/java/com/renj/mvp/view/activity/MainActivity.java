package com.renj.mvp.view.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.renj.arouter.ARouterPath;
import com.renj.arouter.ARouterUtils;
import com.renj.mvp.R;
import com.renj.mvp.utils.MyCommonUtils;
import com.renj.rxsupport.rxview.RxBaseActivity;
import com.renj.utils.res.ResUtils;
import com.renj.view.MyViewPager;

import java.util.ArrayList;
import java.util.List;


@Route(path = ARouterPath.PATH_MAIN_ACTIVITY_MAIN)
public class MainActivity extends RxBaseActivity {
    MyViewPager viewPager;
    BottomNavigationBar bottomTab;

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.main_activity;
    }

    @Override
    public void initData() {
        viewPager = findViewById(R.id.view_pager);
        bottomTab = findViewById(R.id.bottom_tab);
        setPageBack(false, false, null);
        initTitles();
        initFragments();
        initViewPager();
        initNavigationBar();
    }

    private void initTitles() {
        titles.add(ResUtils.getString(R.string.main_tab_1));
        titles.add(ResUtils.getString(R.string.main_tab_2));
        titles.add(ResUtils.getString(R.string.main_tab_3));
        setPageTitle(titles.get(0));
    }

    private void initFragments() {
        fragments.add(ARouterUtils.getFragment(ARouterPath.PATH_HOME_FRAGMENT_HOME));
        fragments.add(ARouterUtils.getFragment(ARouterPath.PATH_FOUND_FRAGMENT_FOUND));
        fragments.add(ARouterUtils.getFragment(ARouterPath.PATH_MY_FRAGMENT_MY));
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
                .addItem(new BottomNavigationItem(R.mipmap.home_s, titles.get(0))
                        .setInactiveIconResource(R.mipmap.home_n)
                        .setActiveColor(ResUtils.getColor(R.color.app_main))
                        .setInActiveColor(ResUtils.getColor(R.color.color_gray)))
                .addItem(new BottomNavigationItem(R.mipmap.found_s, titles.get(1))
                        .setInactiveIconResource(R.mipmap.found_n)
                        .setActiveColor(ResUtils.getColor(R.color.app_main))
                        .setInActiveColor(ResUtils.getColor(R.color.color_gray)))
                .addItem(new BottomNavigationItem(R.mipmap.my_s, titles.get(2))
                        .setInactiveIconResource(R.mipmap.my_n)
                        .setActiveColor(ResUtils.getColor(R.color.app_main))
                        .setInActiveColor(ResUtils.getColor(R.color.color_gray)))
                .initialise();

        bottomTab.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                setPageTitle(titles.get(position));
                viewPager.setCurrentItem(position, false);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });

        // 放在后面才能生效
        MyCommonUtils.setBottomNavigationItem(bottomTab, 6, 24, 12);
    }

    @Override
    protected boolean isShowTitleLine() {
        return false;
    }
}
