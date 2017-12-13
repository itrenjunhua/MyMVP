package com.renj.mvp.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.renj.mvp.R;
import com.renj.mvp.app.MyApplication;
import com.renj.mvp.base.dagger.BaseActivityComponent;
import com.renj.mvp.base.dagger.BaseActivityModule;
import com.renj.mvp.base.dagger.DaggerBaseActivityComponent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

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
public abstract class BaseActivity extends RxAppCompatActivity implements IBaseView, View.OnClickListener {
    protected static BaseActivity foregroundActivity;
    private Unbinder bind;
    private View backView;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        // 初始化基本布局
        ViewStub viewTitleBar = (ViewStub) findViewById(R.id.view_title_bar);
        ViewStub viewContent = (ViewStub) findViewById(R.id.view_content);
        initTitleBar(viewTitleBar);
        viewContent.setLayoutResource(getLayoutId());
        viewContent.inflate();

        bind = ButterKnife.bind(this);
        inject(initBaseComponent());
        initPresenter();
        initData();
    }

    /**
     * 初始化标题栏
     *
     * @param viewTitleBar
     */
    private void initTitleBar(ViewStub viewTitleBar) {
        if (isShowTitleBar()) {
            viewTitleBar.setLayoutResource(R.layout.default_title_bar);
            View titleView = viewTitleBar.inflate();
            backView = titleView.findViewById(R.id.title_bar_tv_bck);
            tvTitle = (TextView) titleView.findViewById(R.id.title_bar_title);
            backView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goBack();
                }
            });
        }
    }

    /**
     * 需要修改返回按钮的功能时，重写该方法即可
     */
    protected void goBack() {
        finish();
    }

    /**
     * 设置标题，前提是 {@link #isShowTitleBar()} 方法返回 true
     *
     * @param titleMsg 标题显示内容
     */
    public void setTitle(String titleMsg) {
        setTitle(titleMsg, true);
    }

    /**
     * 设置标题，前提是 {@link #isShowTitleBar()} 方法返回 true
     *
     * @param titleMsg   标题显示内容
     * @param isShowBack 是否需要显示返回按钮 true 显示；false 不显示
     */
    public void setTitle(String titleMsg, boolean isShowBack) {
        if (isShowTitleBar()) {
            if (tvTitle != null)
                tvTitle.setText(titleMsg);

            if (backView != null)
                backView.setVisibility(isShowBack ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 是否需要显示标题栏，子类可跟据需求重写该方法控制是否需要显示标题栏
     *
     * @return true：需要显示；false：不需要显示
     */
    protected boolean isShowTitleBar() {
        return true;
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
     * 处理点击事件时，只需要重写该方法即可，不需要再实现{@link View.OnClickListener}接口
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

    @Override
    public void stateError() {

    }

    @Override
    public void stateEmpty() {

    }

    @Override
    public void stateLoading() {

    }

    @Override
    public void stateContent() {

    }

    /**
     * 获取当前正在前台的Activity
     *
     * @return
     */
    public static BaseActivity getForegroundActivity() {
        return foregroundActivity;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
