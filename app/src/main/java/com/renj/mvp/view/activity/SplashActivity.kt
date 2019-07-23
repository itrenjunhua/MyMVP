package com.renj.mvp.view.activity

import android.content.Intent
import com.renj.daggersupport.DaggerSupportActivity
import com.renj.mvp.R
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
class SplashActivity : DaggerSupportActivity() {
    override fun getLayoutId(): Int {
        return R.layout.splash_activity
    }

    override fun initData() {
        splash_image_view.setBackgroundResource(R.mipmap.splash)
        UIUtils.postDelayed({
            var intent = Intent(SplashActivity@ this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}