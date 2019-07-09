package com.renj.mvp.view.fragment

import android.os.Bundle
import com.renj.common.utils.UIUtils
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
        ll_me_see.setOnClickListener { UIUtils.showToastSafe(R.string.me_see) }
        ll_me_collection.setOnClickListener { UIUtils.showToastSafe(R.string.me_collection) }
        ll_me_about.setOnClickListener { UIUtils.showToastSafe(R.string.me_about) }
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
