package com.renj.mvp.view.fragment

import android.os.Bundle
import com.renj.daggersupport.DaggerSupportFragment
import com.renj.mvp.R
import kotlinx.android.synthetic.main.my_fragment.*

/**
 * ======================================================================
 *
 *
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 *
 *
 * 创建时间：2019-04-15   16:59
 *
 *
 * 描述：
 *
 *
 * 修订历史：
 *
 *
 * ======================================================================
 */
class MyFragment : DaggerSupportFragment() {

    override fun getLayoutId(): Int {
        return R.layout.my_fragment
    }

    override fun initData() {
        tv_my.text = "我的测试"
    }

    companion object {
        fun newInstance(): MyFragment {
            val args = Bundle()
            val fragment = MyFragment()
            fragment.arguments = args
            return fragment
        }
    }

}
