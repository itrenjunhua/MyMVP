package com.renj.mvp.view.fragment

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.renj.arouter.ARouterPath
import com.renj.arouter.ARouterUtils
import com.renj.daggersupport.DaggerSupportFragment
import com.renj.mvp.R
import com.renj.utils.common.UIUtils
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
@Route(path = ARouterPath.PATH_FRAGMENT_MY, group = ARouterPath.GROUP_MAIN)
class MyFragment : DaggerSupportFragment() {

    override fun getLayoutId(): Int {
        return R.layout.my_fragment
    }

    override fun initData() {
        ll_me_see.setOnClickListener {
            ARouterUtils.openActivity(ARouterPath.PATH_ACTIVITY_SEE_LIST,ARouterPath.GROUP_MY)
        }
        ll_me_collection.setOnClickListener {
            ARouterUtils.openActivity(ARouterPath.PATH_ACTIVITY_COLLECTION_LIST,ARouterPath.GROUP_MY)
        }
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
