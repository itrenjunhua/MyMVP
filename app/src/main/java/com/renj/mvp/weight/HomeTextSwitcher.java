package com.renj.mvp.weight;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.ViewSwitcher;

import com.renj.common.utils.UIUtils;
import com.renj.mvp.R;
import com.renj.mvp.mode.bean.HomeListRPB;

import java.util.ArrayList;
import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-07-01   15:49
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class HomeTextSwitcher extends TextSwitcher {
    private int index = 0;
    private Handler handler = new Handler();
    private boolean isFlipping = false; // 是否启用预警信息轮播
    private List<HomeListRPB.NoticeBean> mTextList = new ArrayList<>();

    public HomeTextSwitcher(Context context) {
        this(context, null);
    }

    public HomeTextSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(final Context context) {
        setInAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_bottom));
        setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_out_top));
        setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                View textView = LayoutInflater.from(context).inflate(R.layout.home_scroll_view, HomeTextSwitcher.this, false);
                textView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mTextList.size() > 0)
                            UIUtils.showToastSafe(mTextList.get(index).title);
                    }
                });
                return textView;
            }
        });
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!isFlipping) {
                return;
            }
            index++;
            setText(mTextList.get(index % mTextList.size()).title);
            if (index == mTextList.size()) {
                index = 0;
            }
            startFlipping();
        }
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
    public void setData(List<HomeListRPB.NoticeBean> textList) {
        this.mTextList = textList;
        if (mTextList.size() == 1) {
            setText(mTextList.get(0).title);
            index = 0;
        }
        if (mTextList.size() > 1) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setText(HomeTextSwitcher.this.mTextList.get(0).title);
                    index = 0;
                }
            }, 1000);
            startFlipping();
        }
    }

}
