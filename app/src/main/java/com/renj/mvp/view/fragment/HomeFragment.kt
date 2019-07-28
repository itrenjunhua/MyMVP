package com.renj.mvp.view.fragment

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.renj.arouter.ARouterPath
import com.renj.arouter.ARouterUtils
import com.renj.mvp.R
import com.renj.mvpbase.view.BaseFragment
import com.renj.utils.res.ResUtils
import kotlinx.android.synthetic.main.home_fragment.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView


/**
 * ======================================================================
 *
 * 作者：Renj
 *
 * 创建时间：2019-07-07   16:59
 *
 * 描述：首页 Fragment
 *
 * 修订历史：
 *
 * ======================================================================
 */
@Route(path = ARouterPath.PATH_FRAGMENT_HOME, group = ARouterPath.GROUP_MAIN)
class HomeFragment : BaseFragment() {

    val titles: Array<String> by lazy {
        arrayOf(
                ResUtils.getString(R.string.home_top_my_csdn),
                ResUtils.getString(R.string.home_top_my_github)
        )
    }
    var fragments = ArrayList<Fragment>()

    override fun getLayoutId(): Int {
        return R.layout.home_fragment
    }

    override fun initData() {
        fragments.add(ARouterUtils.getFragment(ARouterPath.PATH_FRAGMENT_MY_CSDN, ARouterPath.GROUP_MY_BLOG))
        fragments.add(ARouterUtils.getFragment(ARouterPath.PATH_FRAGMENT_MY_GITHUB, ARouterPath.GROUP_MY_BLOG))
        initViewPager()
        initIndicator()
    }

    private fun initViewPager() {
        home_view_pager.adapter = object : FragmentPagerAdapter(childFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return fragments[position]
            }

            override fun getCount(): Int {
                return fragments.size
            }
        }
    }

    private fun initIndicator() {
        val commonNavigator = CommonNavigator(activity)
        commonNavigator.isAdjustMode = true
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return if (titles == null) 0 else titles.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val colorTransitionPagerTitleView = ColorTransitionPagerTitleView(context)
                colorTransitionPagerTitleView.normalColor = ResUtils.getColor(R.color.main_text)
                colorTransitionPagerTitleView.selectedColor = ResUtils.getColor(R.color.app_main)
                colorTransitionPagerTitleView.text = titles[index]
                colorTransitionPagerTitleView.setOnClickListener {
                    home_view_pager.currentItem = index
                }
                return colorTransitionPagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_WRAP_CONTENT
                indicator.setColors(ResUtils.getColor(R.color.app_main))
                return indicator
            }
        }
        home_magic_indicator.navigator = commonNavigator
        ViewPagerHelper.bind(home_magic_indicator, home_view_pager)
    }
}