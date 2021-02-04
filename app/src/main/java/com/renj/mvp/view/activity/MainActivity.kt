package com.renj.mvp.view.activity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.view.View
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.renj.mvp.R
import com.renj.mvp.utils.MyCommonUtils
import com.renj.mvp.view.fragment.FoundFragment
import com.renj.mvp.view.fragment.HomeFragment
import com.renj.mvp.view.fragment.MyFragment
import com.renj.mvpbase.view.BaseFragment
import com.renj.rxsupport.rxview.RxBaseActivity
import com.renj.utils.res.ResUtils
import kotlinx.android.synthetic.main.main_activity.*
import java.util.*


class MainActivity : RxBaseActivity() {

    private val fragments = ArrayList<BaseFragment>()
    private val titles = ArrayList<String>()

    override fun getLayoutId(): Int {
        return R.layout.main_activity
    }

    override fun initView(contentView: View?) {
        initTitles()
        initNavigationBar()
    }

    override fun initData() {
        initFragments()
        initViewPager()
    }

    private fun initTitles() {
        titles.add(ResUtils.getString(R.string.main_tab_1))
        titles.add(ResUtils.getString(R.string.main_tab_2))
        titles.add(ResUtils.getString(R.string.main_tab_3))
        setPageTitle(titles[0])
    }

    private fun initFragments() {
        fragments.add(HomeFragment.newInstance())
        fragments.add(FoundFragment.newInstance())
        fragments.add(MyFragment.newInstance())
    }

    private fun initViewPager() {
        view_pager.adapter = (object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return fragments[position]
            }

            override fun getCount(): Int {
                return fragments.size
            }
        })
    }

    private fun initNavigationBar() {
        bottom_tab
                .setMode(BottomNavigationBar.MODE_FIXED)
                .addItem(BottomNavigationItem(R.mipmap.home_s, titles[0])
                        .setInactiveIconResource(R.mipmap.home_n)
                        .setActiveColor(ResUtils.getColor(R.color.app_main))
                        .setInActiveColor(ResUtils.getColor(R.color.color_gray)))
                .addItem(BottomNavigationItem(R.mipmap.found_s, titles[1])
                        .setInactiveIconResource(R.mipmap.found_n)
                        .setActiveColor(ResUtils.getColor(R.color.app_main))
                        .setInActiveColor(ResUtils.getColor(R.color.color_gray)))
                .addItem(BottomNavigationItem(R.mipmap.me_s, titles[2])
                        .setInactiveIconResource(R.mipmap.me_n)
                        .setActiveColor(ResUtils.getColor(R.color.app_main))
                        .setInActiveColor(ResUtils.getColor(R.color.color_gray)))
                .initialise()

        bottom_tab.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
            override fun onTabSelected(position: Int) {
                setPageTitle(titles[position])
                view_pager.setCurrentItem(position, false)
            }

            override fun onTabUnselected(position: Int) {

            }

            override fun onTabReselected(position: Int) {

            }
        })

        // 放在后面才能生效
        MyCommonUtils.setBottomNavigationItem(bottom_tab, 6, 24, 12)
    }

    override fun isShowTitleBar(): Boolean {
        return false
    }
}
