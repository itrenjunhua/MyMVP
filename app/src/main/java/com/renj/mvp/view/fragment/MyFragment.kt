package com.renj.mvp.view.fragment

import android.content.Intent
import android.os.Bundle
import com.renj.mvp.R
import com.renj.mvp.view.activity.CollectionListActivity
import com.renj.mvp.view.activity.SeeListActivity
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
class MyFragment : RxBaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.my_fragment
    }

    override fun initView() {
    }

    override fun initData() {
        ll_me_see.setOnClickListener {
            var intent = Intent(activity, SeeListActivity::class.java)
            startActivity(intent)
        }
        ll_me_collection.setOnClickListener {
            var intent = Intent(activity, CollectionListActivity::class.java)
            startActivity(intent)
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
