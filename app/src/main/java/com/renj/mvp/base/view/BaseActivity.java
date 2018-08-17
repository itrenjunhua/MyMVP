package com.renj.mvp.base.view;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.renj.mvp.R;
import com.renj.mvp.app.MyApplication;
import com.renj.mvp.base.dagger.BaseActivityComponent;
import com.renj.mvp.base.dagger.BaseActivityModule;
import com.renj.mvp.base.dagger.DaggerBaseActivityComponent;
import com.renj.mvp.mode.bean.BaseResponseBean;
import com.renj.mvp.utils.ResUtils;
import com.renj.mvp.utils.ViewUtils;

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
public abstract class BaseActivity extends AppCompatActivity implements IBaseView, View.OnClickListener {
    protected static BaseActivity foregroundActivity;
    private Unbinder bind;
    private View backView;
    private TextView tvTitle;
    private ViewStub rightView;
    private ViewStub viewTitleBar;

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        // 初始化基本布局
        viewTitleBar = (ViewStub) findViewById(R.id.view_title_bar);
        ViewStub viewContent = (ViewStub) findViewById(R.id.view_content);
        viewContent.setLayoutResource(getLayoutId());
        viewContent.inflate();

        bind = ButterKnife.bind(this);
        inject(initBaseComponent());
        initPresenter();
        initData();
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
     * 是否需要显示标题栏下面的分割线
     *
     * @return true：需要显示；false：不需要显示
     */
    protected boolean isShowLine() {
        return true;
    }

    /**
     * 是否需要显示标题栏中的放回按钮
     *
     * @return true：需要显示；false：不需要显示
     */
    protected boolean isShowBack() {
        return true;
    }

    /**
     * 需要修改返回按钮的功能时，重写该方法即可<br/>
     * <b>注意：
     * 如果调用了 {@link #setTitleBarView(int)} 方法重置了整个标题栏，那么这个方法将失去意义</b>
     */
    protected void goBack() {
        finish();
    }

    /**
     * 自定义整个标题栏<br/>
     * <b>注意：
     * 如果调用了 {@link #setPageTitle(String)}、{@link #setPageTitle(String, boolean)}、{@link #setTitleBarRightViewText(String, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewText(String, float, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, float, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewImg(int, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightView(int)} 中的任何一个，方法 {@link #setTitleBarView(int)} 将失效，反之如此。</b>
     *
     * @param layoutId 标题栏布局id
     * @return 返回参数所表示的布局文件(参数布局文件的根布局对象)，<b>注意：如果 {@link #isShowTitleBar()} 方法返回 false或找不到布局ID，会返回 null</b>
     */
    protected View setTitleBarView(@LayoutRes int layoutId) {
        if (!isShowTitleBar() || viewTitleBar.getLayoutResource() != 0) return null;

        viewTitleBar.setLayoutResource(layoutId);
        return viewTitleBar.inflate();
    }

    /**
     * 初始化标题栏
     */
    private void initTitleBar() {
        if (viewTitleBar.getLayoutResource() == 0) {
            viewTitleBar.setLayoutResource(R.layout.default_title_bar);
            View titleView = viewTitleBar.inflate();
            backView = titleView.findViewById(R.id.title_bar_tv_bck);
            tvTitle = (TextView) titleView.findViewById(R.id.title_bar_title);
            rightView = (ViewStub) titleView.findViewById(R.id.title_bar_right_view);
            View viewLine = titleView.findViewById(R.id.title_line);

            if (isShowBack())
                ViewUtils.showView(backView);
            else
                ViewUtils.goneView(backView);

            if (isShowLine())
                ViewUtils.showView(viewLine);
            else
                ViewUtils.goneView(viewLine);

            // 返回按钮设置监听
            backView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goBack();
                }
            });
        }
    }

    /**
     * 设置标题内容，前提是 {@link #isShowTitleBar()} 方法返回 true，默认返回按钮显示<br/>
     * <b>注意：
     * 如果调用了 {@link #setPageTitle(String)}、{@link #setPageTitle(String, boolean)}、{@link #setTitleBarRightViewText(String, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewText(String, float, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, float, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewImg(int, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightView(int)} 中的任何一个，方法 {@link #setTitleBarView(int)} 将失效，反之如此。</b>
     *
     * @param titleMsg 标题显示内容
     */
    public void setPageTitle(@NonNull String titleMsg) {
        setPageTitle(titleMsg, true);
    }

    /**
     * 设置标题内容和是否显示返回按钮，前提是 {@link #isShowTitleBar()} 方法返回 true<br/>
     * <b>注意：
     * 如果调用了 {@link #setPageTitle(String)}、{@link #setPageTitle(String, boolean)}、{@link #setTitleBarRightViewText(String, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewText(String, float, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, float, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewImg(int, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightView(int)} 中的任何一个，方法 {@link #setTitleBarView(int)} 将失效，反之如此。</b>
     *
     * @param titleMsg   标题显示内容
     * @param isShowBack 是否需要显示返回按钮 true 显示；false 不显示
     */
    public void setPageTitle(@NonNull String titleMsg, @NonNull boolean isShowBack) {
        if (isShowTitleBar()) {
            initTitleBar();

            if (tvTitle != null)
                tvTitle.setText(titleMsg);

            if (backView != null)
                backView.setVisibility(isShowBack ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 设置标题栏右边展示文字信息，前提是 {@link #isShowTitleBar()} 方法返回 true<br/>
     * <b>注意：
     * 如果调用了 {@link #setPageTitle(String)}、{@link #setPageTitle(String, boolean)}、{@link #setTitleBarRightViewText(String, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewText(String, float, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, float, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewImg(int, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightView(int)} 中的任何一个，方法 {@link #setTitleBarView(int)} 将失效，反之如此。</b>
     *
     * @param rightMsg 右边显示的文字
     * @param listener 文字监听，不需要事件可以传null
     */
    public void setTitleBarRightViewText(@NonNull String rightMsg, OnTitleRightClickListener listener) {
        setTitleBarRightViewText(rightMsg, ResUtils.getDimens(R.dimen.title_bar_right_size), ResUtils.getColor(R.color.title_bar_right_color), listener);
    }

    /**
     * 设置标题栏右边展示文字信息，前提是 {@link #isShowTitleBar()} 方法返回 true<br/>
     * <b>注意：
     * 如果调用了 {@link #setPageTitle(String)}、{@link #setPageTitle(String, boolean)}、{@link #setTitleBarRightViewText(String, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewText(String, float, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, float, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewImg(int, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightView(int)} 中的任何一个，方法 {@link #setTitleBarView(int)} 将失效，反之如此。</b>
     *
     * @param rightMsg 右边显示的文字
     * @param textSize 右边显示的文字大小，传入的参数单位为 px，默认15sp
     * @param listener 文字监听，不需要事件可以传null
     */
    public void setTitleBarRightViewText(@NonNull String rightMsg, @NonNull float textSize, OnTitleRightClickListener listener) {
        setTitleBarRightViewText(rightMsg, textSize, ResUtils.getColor(R.color.title_bar_right_color), listener);
    }

    /**
     * 设置标题栏右边展示文字信息，前提是 {@link #isShowTitleBar()} 方法返回 true<br/>
     * <b>注意：
     * 如果调用了 {@link #setPageTitle(String)}、{@link #setPageTitle(String, boolean)}、{@link #setTitleBarRightViewText(String, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewText(String, float, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, float, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewImg(int, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightView(int)} 中的任何一个，方法 {@link #setTitleBarView(int)} 将失效，反之如此。</b>
     *
     * @param rightMsg  右边显示的文字
     * @param textColor 右边显示的文字颜色，默认  <color name="title_bar_right_color">#FF333333</color>
     * @param listener  文字监听，不需要事件可以传null
     */
    public void setTitleBarRightViewText(@NonNull String rightMsg, @NonNull int textColor, OnTitleRightClickListener listener) {
        setTitleBarRightViewText(rightMsg, ResUtils.getDimens(R.dimen.title_bar_right_size), textColor, listener);
    }

    /**
     * 设置标题栏右边展示文字信息，前提是 {@link #isShowTitleBar()} 方法返回 true<br/>
     * <b>注意：
     * 如果调用了 {@link #setPageTitle(String)}、{@link #setPageTitle(String, boolean)}、{@link #setTitleBarRightViewText(String, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewText(String, float, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, float, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewImg(int, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightView(int)} 中的任何一个，方法 {@link #setTitleBarView(int)} 将失效，反之如此。</b>
     *
     * @param rightMsg  右边显示的文字
     * @param textSize  右边显示的文字大小，传入的参数单位为 px，默认15sp
     * @param textColor 右边显示的文字颜色，默认  <color name="title_bar_right_color">#FF333333</color>
     * @param listener  文字监听，不需要事件可以传null
     */
    public void setTitleBarRightViewText(@NonNull String rightMsg, @NonNull float textSize, @NonNull int textColor, final OnTitleRightClickListener listener) {
        if (isShowTitleBar()) {
            initTitleBar();
            if (rightView != null) {
                rightView.setLayoutResource(R.layout.default_title_bar_right_text);
                final TextView textView = (TextView) rightView.inflate();

                textView.setText(rightMsg);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                textView.setTextColor(textColor);

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null)
                            listener.onRightViewClick(textView);
                    }
                });
            }
        }
    }

    /**
     * 设置标题栏右边展示图片，前提是 {@link #isShowTitleBar()} 方法返回 true<br/>
     * <b>注意：
     * 如果调用了 {@link #setPageTitle(String)}、{@link #setPageTitle(String, boolean)}、{@link #setTitleBarRightViewText(String, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewText(String, float, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, float, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewImg(int, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightView(int)} 中的任何一个，方法 {@link #setTitleBarView(int)} 将失效，反之如此。</b>
     *
     * @param resId    右边显示的图片ID
     * @param listener 文字监听，不需要事件可以传null
     */
    public void setTitleBarRightViewImg(@DrawableRes int resId, final OnTitleRightClickListener listener) {
        if (isShowTitleBar()) {
            initTitleBar();
            if (rightView != null) {
                rightView.setLayoutResource(R.layout.default_title_bar_right_img);

                final ImageView imageView = (ImageView) rightView.inflate();
                imageView.setBackgroundResource(resId);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null)
                            listener.onRightViewClick(imageView);
                    }
                });
            }
        }
    }

    /**
     * 设置标题栏右边自定义布局，前提是 {@link #isShowTitleBar()} 方法返回 true，<b>注意：控件里面的id必须使用findViewById()方式获得，不能使用注解的方式，会出现异常</b><br/>
     * <b>注意：
     * 如果调用了 {@link #setPageTitle(String)}、{@link #setPageTitle(String, boolean)}、{@link #setTitleBarRightViewText(String, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewText(String, float, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, float, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewImg(int, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightView(int)} 中的任何一个，方法 {@link #setTitleBarView(int)} 将失效，反之如此。</b>
     *
     * @param layoutId 自定义布局的id
     * @return 返回参数所表示的布局文件(参数布局文件的根布局对象)，<b>注意：如果 {@link #isShowTitleBar()} 方法返回 false，那么这里会返回 null</b>
     */
    public View setTitleBarRightView(@LayoutRes int layoutId) {
        if (isShowTitleBar()) {
            initTitleBar();
            if (rightView != null) {
                rightView.setLayoutResource(layoutId);
                return rightView.inflate();
            }
        }
        return null;
    }

    /**
     * 初始化Presenter，在{@link BasePresenterActivity}中重写
     */
    protected void initPresenter() {

    }

    /**
     * 初始化{@link BaseActivityComponent}
     *
     * @return
     */
    @NonNull
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
    public <E> void showContentPage(@NonNull E e) {

    }

    @Override
    public void showLoadingPage() {

    }

    @Override
    public <E extends BaseResponseBean> void showEmptyDataPage(@NonNull E e) {

    }

    @Override
    public void showNetWorkErrorPage() {

    }

    @Override
    public void showErrorPage(Throwable e) {

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

    public interface OnTitleRightClickListener {
        void onRightViewClick(View view);
    }
}