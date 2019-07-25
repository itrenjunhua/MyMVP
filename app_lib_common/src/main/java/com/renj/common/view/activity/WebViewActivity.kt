package com.renj.common.view.activity

import android.os.Build
import android.support.annotation.RequiresApi
import android.view.KeyEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.renj.arouter.ARouterPath
import com.renj.common.R
import com.renj.common.mode.bean.bundle.WebActivityBundleData
import com.renj.common.mode.bean.bundle.WebActivityType.TYPE_LIST
import com.renj.common.mode.bean.data.GeneralListBean
import com.renj.daggersupport.DaggerSupportPresenterActivity
import com.renj.mvp.controller.IWebViewController
import com.renj.mvp.presenter.WebViewPresenter
import kotlinx.android.synthetic.main.web_view_activity.*


/**
 * ======================================================================
 *
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 *
 * 创建时间：2019-07-08   14:46
 *
 * 描述：
 *
 * 修订历史：
 *
 * ======================================================================
 */
@Route(path = ARouterPath.PATH_ACTIVITY_WEB, group = ARouterPath.GROUP_COMMON)
class WebViewActivity : DaggerSupportPresenterActivity<WebViewPresenter>(), IWebViewController.IWebViewView {

    private var collectionStatus = false

    @JvmField
    @Autowired(name = "data")
    var bundleData: WebActivityBundleData? = null

    override fun getLayoutId(): Int {
        return R.layout.web_view_activity
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initData() {

        bundleData?.title?.let { setPageTitle(it) }
        setPageBack(true, false, null)
        ll_web_view_bottom.visibility = if (bundleData!!.type == TYPE_LIST) View.VISIBLE else View.GONE

        if (bundleData!!.type == TYPE_LIST) {
            handlerSeeCount(bundleData)
            mPresenter.getCollectionStatus(bundleData!!.pid, bundleData!!.id)

            iv_collection.setOnClickListener {
                mPresenter.changeCollectionStatus(bundleData!!.pid, bundleData!!.id, !collectionStatus)
            }
        }

        webSetting()
        wev_view.loadUrl(bundleData!!.url)
    }

    private fun handlerSeeCount(bundleData: WebActivityBundleData?) {
        var generalListBean = GeneralListBean()
        generalListBean.pid = bundleData!!.pid
        generalListBean.id = bundleData!!.id
        generalListBean.title = bundleData!!.title
        generalListBean.content = bundleData!!.content
        generalListBean.url = bundleData!!.url
        generalListBean.images = bundleData!!.images
        mPresenter.addSeeCount(generalListBean)
    }

    override fun addSeeCountSuccess(seeCount: Long) {
        tv_watch_count.text = seeCount.toString()
    }

    override fun getCollectionStatusSuccess(boolean: Boolean) {
        this@WebViewActivity.collectionStatus = boolean
        iv_collection.setBackgroundResource(if (this@WebViewActivity.collectionStatus) R.mipmap.collection_s else R.mipmap.collection_n)
    }

    override fun changeCollectionStatusSuccess(collectionStatus: Boolean) {
        this@WebViewActivity.collectionStatus = !this@WebViewActivity.collectionStatus
        iv_collection.setBackgroundResource(if (this@WebViewActivity.collectionStatus) R.mipmap.collection_s else R.mipmap.collection_n)
    }

    /**
     * webView相关设置
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun webSetting() {
        // 获取WebView的设置对象
        val settings = wev_view.settings

        // 设置WebView显示放大和缩小的按钮
        //settings.setBuiltInZoomControls(true);

        // 设置双击放大和缩小
        //settings.setUseWideViewPort(true);

        // 设置可以加载JavaScript脚本
        settings.javaScriptEnabled = true

        // 允许https中嵌套http  Android 5.0及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

        wev_view.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                // 强制让超链接在当前的WebView中打开
                view.loadUrl(url)
                return true
            }
        }

        wev_view.webChromeClient = object : WebChromeClient() {
            /**
             * 加载进度改变时调用
             * @param view 当前的WebView对象
             * @param newProgress 当前的进度
             */
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (100 <= newProgress) {
                    progress_web_view.visibility = View.GONE
                } else {
                    progress_web_view.max = 100
                    progress_web_view.progress = newProgress
                    progress_web_view.visibility = View.VISIBLE
                }
            }

            /**
             * 正在加载的标题
             * @param view WebView对象
             * @param title 加载的标题
             */
            override fun onReceivedTitle(view: WebView, title: String) {
                setPageTitle(title)
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.repeatCount === 0) {
            goPrePage()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    /**
     * 返回上一页，先判断WebView是否为最上层网页，如果不是，返回到上一层网页，否则退出当前的Activity
     */
    private fun goPrePage() {
        if (null != wev_view && wev_view.canGoBack()) {
            wev_view.goBack()
        } else {
            finish()
        }
    }

    override fun onDestroy() {
        wev_view.destroy()
        super.onDestroy()
    }

}