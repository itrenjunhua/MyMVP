package com.renj.my.view.fragment

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.renj.arouter.ARouterPath
import com.renj.arouter.ARouterUtils
import com.renj.my.R
import com.renj.rxsupport.rxview.RxBaseFragment
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
@Route(path = ARouterPath.PATH_MY_FRAGMENT_MY)
class MyFragment : RxBaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.my_fragment
    }

    override fun initData() {
        ll_me_see.setOnClickListener {
            ARouterUtils.openActivity(ARouterPath.PATH_MY_ACTIVITY_SEE_LIST)
        }
        ll_me_collection.setOnClickListener {
            ARouterUtils.openActivity(ARouterPath.PATH_MY_ACTIVITY_COLLECTION_LIST)
        }
        ll_me_about.setOnClickListener { UIUtils.showToastSafe(R.string.my_about) }
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
