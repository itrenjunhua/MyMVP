package com.renj.home.weight;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;

import com.renj.arouter.ARouterPath;
import com.renj.arouter.ARouterUtils;
import com.renj.common.mode.bean.bundle.WebActivityBundleData;
import com.renj.common.mode.bean.bundle.WebActivityType;
import com.renj.home.R;
import com.renj.home.mode.bean.data.NoticeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：itrenjunhua@163.com
 * <p>
 * 创建时间：2019-07-01   15:49
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class NoticeTextSwitcher extends TextSwitcher {
    private int index = 0;
    private Handler handler = new Handler();
    private boolean isFlipping = false; // 是否启用信息轮播
    private List<NoticeBean> mTextList = new ArrayList<>();

    public NoticeTextSwitcher(Context context) {
        this(context, null);
    }

    public NoticeTextSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(final Context context) {
        setInAnimation(AnimationUtils.loadAnimation(context, R.anim.common_slide_in_bottom));
        setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.common_slide_out_top));
        setFactory(() -> {
            View textView = LayoutInflater.from(context).inflate(R.layout.cell_notice_view, NoticeTextSwitcher.this, false);
            textView.setOnClickListener((v) -> {
                NoticeBean noticeBean = mTextList.get(index);
                WebActivityBundleData bundleData = new WebActivityBundleData(0, noticeBean.id, noticeBean.title, "", noticeBean.url, new ArrayList<>(), WebActivityType.TYPE_NOTICE);
                ARouterUtils.openActivity(ARouterPath.PATH_COMMON_ACTIVITY_WEB,"data", bundleData);
            });
            return textView;
        });
    }

    private Runnable runnable = () -> {
        if (!isFlipping) {
            return;
        }
        index++;
        setText(mTextList.get(index % mTextList.size()).title);
        if (index == mTextList.size()) {
            index = 0;
        }
        startFlipping();
    };

    //开启信息轮播
    public void startFlipping() {
        if (mTextList.size() > 1) {
            handler.removeCallbacks(runnable);
            isFlipping = true;
            handler.postDelayed(runnable, 3000);
        }
    }

    //关闭信息轮播
    public void stopFlipping() {
        if (mTextList.size() > 1) {
            isFlipping = false;
            handler.removeCallbacks(runnable);
        }
    }

    //设置数据
    public void setData(List<NoticeBean> textList) {
        this.mTextList = textList;
        if (mTextList.size() == 1) {
            setText(mTextList.get(0).title);
            index = 0;
        }
        if (mTextList.size() > 1) {
            handler.postDelayed(() -> {
                setText(NoticeTextSwitcher.this.mTextList.get(0).title);
                index = 0;
            }, 1000);
            startFlipping();
        }
    }

}
