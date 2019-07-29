package com.renj.mvp.view.activity

import com.renj.common.utils.aroute.ARouterPath
import com.renj.common.utils.aroute.ARouterUtils
import com.renj.mvp.R
import com.renj.rxsupport.rxview.RxBaseActivity
import com.renj.utils.common.UIUtils
import kotlinx.android.synthetic.main.splash_activity.*

/**
 * ======================================================================
 *
 * 作者：Renj
 *
 * 创建时间：2019-07-08   1:03
 *
 * 描述：
 *
 * 修订历史：
 *
 * ======================================================================
 */
class SplashActivity : RxBaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.splash_activity
    }

    override fun initData() {
        splash_image_view.setBackgroundResource(R.mipmap.splash)
        UIUtils.postDelayed({
            ARouterUtils.openActivity(ARouterPath.PATH_MAIN_ACTIVITY_MAIN)
            finish()
        }, 3000)
    }
}