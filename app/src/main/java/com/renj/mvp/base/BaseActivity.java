package com.renj.mvp.base;

import android.os.Bundle;
import android.view.View;

import com.renj.mvp.app.MyApplication;
import com.renj.mvp.base.dagger.BaseActivityComponent;
import com.renj.mvp.base.dagger.BaseActivityModule;
import com.renj.mvp.base.dagger.DaggerBaseActivityComponent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-11   18:39
 * <p>
 * 描述：Activity的基类<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * 如果一个新的Activity不需要访问网络，那么就直接继承{@link BaseActivity}<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * 如果一个新的Activity需要访问防落，那么可以继承{@link BasePresenterActivity}
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public abstract class BaseActivity extends RxAppCompatActivity implements BaseControl.IView, View.OnClickListener {
    protected static BaseActivity foregroundActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.inject(this);
        inject(initBaseComponent());
        initPresenter();
        initData();
    }

    /**
     * 初始化Presenter，在{@link BasePresenterActivity}中重写
     */
    void initPresenter() {

    }

    /**
     * 初始化{@link BaseActivityComponent}
     *
     * @return
     */
    private BaseActivityComponent initBaseComponent() {
        return DaggerBaseActivityComponent.builder()
                .applicationComponent(MyApplication.mApplicationComponent)
                .baseActivityModule(new BaseActivityModule())
                .build();
    }

    /**
     * 当有对象是通过注入的方式初始化的，那么需要重写该方法<br/>
     * <b>提示：</b><br/>
     * <b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;该方法的一般写法：
     * <code>activityComponent.inject(this);</code>
     * </b><br/>
     * <b>注意：</b><br/>
     * <b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * 如上重写该方法，需要在{@link BaseActivityComponent}中添加代码
     * <code>void inject(CustomActivity customActivity);</code>
     * </b>
     *
     * @param activityComponent
     */
    protected abstract void inject(BaseActivityComponent activityComponent);

    @Override
    public void onClick(View v) {
        handlerClick(v, v.getId());
    }

    /**
     * 处理点击事件时，只需要重写该方法即可，不需要再实现{@link android.view.View.OnClickListener}接口
     *
     * @param v   控件
     * @param vId
     */
    protected void handlerClick(View v, int vId) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        foregroundActivity = this;
    }

    /**
     * 获取当前正在前台的Activity
     *
     * @return
     */
    public static BaseActivity getForegroundActivity() {
        return foregroundActivity;
    }
}
