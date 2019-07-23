package com.renj.mvpbase.view;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.renj.mvpbase.R;
import com.renj.utils.common.ActivityManager;
import com.renj.utils.common.UIUtils;
import com.renj.utils.common.ViewUtils;
import com.renj.utils.res.ResUtils;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

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
 * 如果一个新的Activity需要访问网络，那么可以继承{@link BasePresenterActivity}
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public abstract class BaseActivity extends SupportActivity implements IBaseView, View.OnClickListener {
    private SparseArray<View> titleViews = new SparseArray<>();
    private Unbinder bind;
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
        beforeOnCreateSuper(savedInstanceState);
        super.onCreate(savedInstanceState);
        // 注册 ARouter 路由
        ARouter.getInstance().inject(this);
        setContentView(R.layout.activity_base);
        ActivityManager.addActivity(this);
        // 初始化基本布局ViewStub
        viewTitleBar = findViewById(R.id.view_title_bar);
        ViewStub vsContent = findViewById(R.id.view_content);
        vsContent.setLayoutResource(getLayoutId());
        View contentView = vsContent.inflate();
        initRPageStatusController(contentView);
        bind = ButterKnife.bind(this, contentView);
        initPresenter();
        initData();
    }

    /**
     * 在{@link BasePresenterActivity}中重写，初始化页面控制器
     *
     * @param view
     * @return
     */
    protected void initRPageStatusController(View view) {

    }

    /**
     * 在 {@code super.onCreate(savedInstanceState);} 之前调用
     *
     * @param savedInstanceState
     */
    protected void beforeOnCreateSuper(Bundle savedInstanceState) {
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isShouldHideSoftKeyBoard() && ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v != null && ViewUtils.isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    /**
     * 是否需要点击非 {@link android.widget.EditText} 控件之外的地方就隐藏软键盘。默认返回true：是
     *
     * @return tru：点击的位置在 {@link android.widget.EditText} 控件的范围，需要隐藏软键盘  false：点击的位置在非 {@link android.widget.EditText} 控件范围，需要隐藏
     */
    protected boolean isShouldHideSoftKeyBoard() {
        return true;
    }

    /**
     * 是否需要显示标题栏，子类可跟据需求重写该方法控制是否需要显示标题栏<br/>
     * <b>注意：需要真正的显示出标题栏，至少需要调用
     * {@link #setPageBack(boolean, boolean, int)}、{@link #setPageBack(boolean, boolean, String)}、
     * {@link #setPageTitle(int)}、{@link #setPageTitle(String)}、{@link #setTitleBarRightViewText(String, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewText(String, float, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, float, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewImg(int, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightView(int)} 方法中的其中一个</b>
     *
     * @return true：需要显示；false：不需要显示
     */
    protected boolean isShowTitleBar() {
        return true;
    }

    /**
     * 是否显示标题分割线，默认显示
     *
     * @return
     */
    protected boolean isShowTitleLine() {
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
     * 如果调用了
     * {@link #setPageBack(boolean, boolean, int)}、{@link #setPageBack(boolean, boolean, String)}、
     * {@link #setPageTitle(int)}、{@link #setPageTitle(String)}、{@link #setTitleBarRightViewText(String, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewText(String, float, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, float, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewImg(int, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightView(int)} 方法中的任何一个，那么方法 {@link #setTitleBarView(int)} 将失效，反之如此。</b>
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
            View backView = titleView.findViewById(R.id.title_bar_tv_bck);
            TextView tvBack = titleView.findViewById(R.id.tv_back);
            TextView tvTitle = titleView.findViewById(R.id.title_bar_title);
            ViewStub rightView = titleView.findViewById(R.id.title_bar_right_view);
            View viewLine = titleView.findViewById(R.id.title_line);

            titleViews.put(R.id.title_bar_tv_bck, backView);
            titleViews.put(R.id.tv_back, tvBack);
            titleViews.put(R.id.title_bar_title, tvTitle);
            titleViews.put(R.id.title_bar_right_view, rightView);
            titleViews.put(R.id.title_line, viewLine);

            if (isShowTitleLine()) {
                viewLine.setVisibility(View.VISIBLE);
            } else {
                viewLine.setVisibility(View.GONE);
            }

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
     * 设置页面左上角返回控件部分信息，前提是 {@link #isShowTitleBar()} 方法返回 true<br/>
     * <b>注意：
     * 如果调用了
     * {@link #setPageBack(boolean, boolean, int)}、{@link #setPageBack(boolean, boolean, String)}、
     * {@link #setPageTitle(int)}、{@link #setPageTitle(String)}、{@link #setTitleBarRightViewText(String, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewText(String, float, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, float, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewImg(int, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightView(int)} 方法中的任何一个，那么方法 {@link #setTitleBarView(int)} 将失效，反之如此。</b>
     *
     * @param isShowBackView 返回控件是否显示 true：显示，false：不显示
     * @param isShowBackText 返回控件中的“返回” 字样是否显示 true：显示，false：不显示，前提 isShowBackView 为 true
     * @param backTextId     返回控件中的“返回” 字样值修改，前提 isShowBackText 为 true
     */
    public void setPageBack(boolean isShowBackView, boolean isShowBackText, @StringRes int backTextId) {
        setPageBack(isShowBackView, isShowBackText, getResources().getString(backTextId));
    }

    /**
     * 设置页面左上角返回控件部分信息，前提是 {@link #isShowTitleBar()} 方法返回 true<br/>
     * <b>注意：
     * 如果调用了
     * {@link #setPageBack(boolean, boolean, int)}、{@link #setPageBack(boolean, boolean, String)}、
     * {@link #setPageTitle(int)}、{@link #setPageTitle(String)}、{@link #setTitleBarRightViewText(String, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewText(String, float, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, float, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewImg(int, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightView(int)} 方法中的任何一个，那么方法 {@link #setTitleBarView(int)} 将失效，反之如此。</b>
     *
     * @param isShowBackView 返回控件是否显示 true：显示，false：不显示
     * @param isShowBackText 返回控件中的“返回” 字样是否显示 true：显示，false：不显示，前提 isShowBackView 为 true
     * @param backText       返回控件中的“返回” 字样值修改，前提 isShowBackText 为 true
     */
    public void setPageBack(boolean isShowBackView, boolean isShowBackText, @Nullable String backText) {
        View backView = titleViews.get(R.id.title_bar_tv_bck);
        TextView tvBack = (TextView) titleViews.get(R.id.tv_back);
        if (backView != null) {
            backView.setVisibility(isShowBackView ? View.VISIBLE : View.GONE);
            tvBack.setVisibility(isShowBackText ? View.VISIBLE : View.GONE);
            tvBack.setText(TextUtils.isEmpty(backText) ? "" : backText);
            return;
        }

        if (isShowTitleBar()) {
            initTitleBar();

            titleViews.get(R.id.title_bar_tv_bck).setVisibility(isShowBackView ? View.VISIBLE : View.GONE);
            titleViews.get(R.id.tv_back).setVisibility(isShowBackText ? View.VISIBLE : View.GONE);
            ((TextView) titleViews.get(R.id.tv_back)).setText(TextUtils.isEmpty(backText) ? "" : backText);

        }
    }

    /**
     * 设置标题内容，前提是 {@link #isShowTitleBar()} 方法返回 true<br/>
     * <b>注意：
     * 如果调用了
     * {@link #setPageBack(boolean, boolean, int)}、{@link #setPageBack(boolean, boolean, String)}、
     * {@link #setPageTitle(int)}、{@link #setPageTitle(String)}、{@link #setTitleBarRightViewText(String, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewText(String, float, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, float, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewImg(int, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightView(int)} 方法中的任何一个，那么方法 {@link #setTitleBarView(int)} 将失效，反之如此。</b>
     *
     * @param titleStringId 标题显示内容资源文件
     */
    public void setPageTitle(@StringRes int titleStringId) {
        setPageTitle(getResources().getString(titleStringId));
    }

    /**
     * 设置标题内容，前提是 {@link #isShowTitleBar()} 方法返回 true<br/>
     * <b>注意：
     * 如果调用了
     * {@link #setPageBack(boolean, boolean, int)}、{@link #setPageBack(boolean, boolean, String)}、
     * {@link #setPageTitle(int)}、{@link #setPageTitle(String)}、{@link #setTitleBarRightViewText(String, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewText(String, float, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, float, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewImg(int, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightView(int)} 方法中的任何一个，那么方法 {@link #setTitleBarView(int)} 将失效，反之如此。</b>
     *
     * @param titleMsg 标题显示内容
     */
    public void setPageTitle(@NonNull String titleMsg) {
        TextView tvTitle = (TextView) titleViews.get(R.id.title_bar_title);
        if (tvTitle != null) {
            tvTitle.setText(titleMsg);
            return;
        }

        if (isShowTitleBar()) {
            initTitleBar();

            ((TextView) titleViews.get(R.id.title_bar_title)).setText(titleMsg);
        }
    }

    /**
     * 设置标题栏右边展示文字信息，前提是 {@link #isShowTitleBar()} 方法返回 true<br/>
     * <b>注意：
     * 如果调用了
     * {@link #setPageBack(boolean, boolean, int)}、{@link #setPageBack(boolean, boolean, String)}、
     * {@link #setPageTitle(int)}、{@link #setPageTitle(String)}、{@link #setTitleBarRightViewText(String, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewText(String, float, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, float, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewImg(int, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightView(int)} 方法中的任何一个，那么方法 {@link #setTitleBarView(int)} 将失效，反之如此。</b>
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
     * 如果调用了
     * {@link #setPageBack(boolean, boolean, int)}、{@link #setPageBack(boolean, boolean, String)}、
     * {@link #setPageTitle(int)}、{@link #setPageTitle(String)}、{@link #setTitleBarRightViewText(String, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewText(String, float, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, float, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewImg(int, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightView(int)} 方法中的任何一个，那么方法 {@link #setTitleBarView(int)} 将失效，反之如此。</b>
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
     * 如果调用了
     * {@link #setPageBack(boolean, boolean, int)}、{@link #setPageBack(boolean, boolean, String)}、
     * {@link #setPageTitle(int)}、{@link #setPageTitle(String)}、{@link #setTitleBarRightViewText(String, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewText(String, float, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, float, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewImg(int, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightView(int)} 方法中的任何一个，那么方法 {@link #setTitleBarView(int)} 将失效，反之如此。</b>
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
     * 如果调用了
     * {@link #setPageBack(boolean, boolean, int)}、{@link #setPageBack(boolean, boolean, String)}、
     * {@link #setPageTitle(int)}、{@link #setPageTitle(String)}、{@link #setTitleBarRightViewText(String, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewText(String, float, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, float, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewImg(int, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightView(int)} 方法中的任何一个，那么方法 {@link #setTitleBarView(int)} 将失效，反之如此。</b>
     *
     * @param rightMsg  右边显示的文字
     * @param textSize  右边显示的文字大小，传入的参数单位为 px，默认15sp
     * @param textColor 右边显示的文字颜色，默认  <color name="title_bar_right_color">#FF333333</color>
     * @param listener  文字监听，不需要事件可以传null
     */
    public void setTitleBarRightViewText(@NonNull String rightMsg, @NonNull float textSize, @NonNull int textColor, final OnTitleRightClickListener listener) {
        if (isShowTitleBar()) {
            initTitleBar();
            if (titleViews.get(R.id.title_bar_right_view) != null) {
                ViewStub rightView = (ViewStub) titleViews.get(R.id.title_bar_right_view);
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
     * 如果调用了
     * {@link #setPageBack(boolean, boolean, int)}、{@link #setPageBack(boolean, boolean, String)}、
     * {@link #setPageTitle(int)}、{@link #setPageTitle(String)}、{@link #setTitleBarRightViewText(String, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewText(String, float, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, float, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewImg(int, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightView(int)} 方法中的任何一个，那么方法 {@link #setTitleBarView(int)} 将失效，反之如此。</b>
     *
     * @param resId    右边显示的图片ID
     * @param listener 文字监听，不需要事件可以传null
     */
    public void setTitleBarRightViewImg(@DrawableRes int resId, final OnTitleRightClickListener listener) {
        if (isShowTitleBar()) {
            initTitleBar();
            if (titleViews.get(R.id.title_bar_right_view) != null) {
                ViewStub rightView = (ViewStub) titleViews.get(R.id.title_bar_right_view);
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
     * 如果调用了
     * {@link #setPageBack(boolean, boolean, int)}、{@link #setPageBack(boolean, boolean, String)}、
     * {@link #setPageTitle(int)}、{@link #setPageTitle(String)}、{@link #setTitleBarRightViewText(String, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewText(String, float, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightViewText(String, float, int, OnTitleRightClickListener)}、{@link #setTitleBarRightViewImg(int, OnTitleRightClickListener)}、
     * {@link #setTitleBarRightView(int)} 方法中的任何一个，那么方法 {@link #setTitleBarView(int)} 将失效，反之如此。</b>
     *
     * @param layoutId 自定义布局的id
     * @return 返回参数所表示的布局文件(参数布局文件的根布局对象)，<b>注意：如果 {@link #isShowTitleBar()} 方法返回 false，那么这里会返回 null</b>
     */
    public View setTitleBarRightView(@LayoutRes int layoutId) {
        if (isShowTitleBar()) {
            initTitleBar();
            if (titleViews.get(R.id.title_bar_right_view) != null) {
                ViewStub rightView = (ViewStub) titleViews.get(R.id.title_bar_right_view);
                rightView.setLayoutResource(layoutId);
                return rightView.inflate();
            }
        }
        return null;
    }

    /**
     * 初始化Presenter，在{@link BasePresenterActivity}中重写
     */
    void initPresenter() {

    }

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
    public <E> void showContentPage(@LoadingStyle int loadingStyle, @IntRange int requestCode, @NonNull E e) {
    }

    @Override
    public void showLoadingPage(@LoadingStyle int loadingStyle, @IntRange int requestCode) {
    }

    @Override
    public <E> void showEmptyDataPage(@LoadingStyle int loadingStyle, @IntRange int requestCode, @NonNull E e) {
    }

    @Override
    public void showNetWorkErrorPage(@LoadingStyle int loadingStyle, @IntRange int requestCode) {
    }

    @Override
    public void showErrorPage(@LoadingStyle int loadingStyle, @IntRange int requestCode, Throwable e) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeLoadingDialog();
        ActivityManager.removeActivity(this);
        bind.unbind();
    }

    public interface OnTitleRightClickListener {
        void onRightViewClick(View view);
    }

    /****************  加载对话框处理 ***********/

    protected LoadingDialog loadingDialog;

    @Override
    public void showLoadingDialog() {
        if (loadingDialog != null)
            loadingDialog.close();
        loadingDialog = new LoadingDialog(this);
        loadingDialog.setLoadingText(ResUtils.getString(R.string.dialog_default_loading))
                .setSuccessText(ResUtils.getString(R.string.dialog_default_succeed))
                .setFailedText(ResUtils.getString(R.string.dialog_default_fail))
                .setInterceptBack(false)
                .setLoadSpeed(LoadingDialog.Speed.SPEED_TWO);
        loadingDialog.show();
    }

    @Override
    public void showLoadingDialog(@NonNull String loadingMsg) {
        if (loadingDialog != null)
            loadingDialog.close();
        loadingDialog = new LoadingDialog(this);
        loadingDialog.setLoadingText(loadingMsg)
                .setInterceptBack(false)
                .setLoadSpeed(LoadingDialog.Speed.SPEED_TWO);
        loadingDialog.show();
    }

    @Override
    public void showLoadingDialog(@NonNull String loadingMsg, @NonNull String succeedMsg, @NonNull String failMsg) {
        if (loadingDialog != null)
            loadingDialog.close();
        loadingDialog = new LoadingDialog(this);
        loadingDialog.setLoadingText(loadingMsg)
                .setSuccessText(succeedMsg)
                .setFailedText(failMsg)
                .setInterceptBack(false)
                .setLoadSpeed(LoadingDialog.Speed.SPEED_TWO);
        loadingDialog.show();
    }

    @Override
    public void closeLoadingDialog() {
        if (loadingDialog != null)
            loadingDialog.close();
        loadingDialog = null;
    }

    @Override
    public void closeSucceedDialog() {
        closeSucceedDialog(1800);
    }

    @Override
    public void closeSucceedDialog(long millis) {
        if (loadingDialog != null) {
            loadingDialog.loadSuccess();
            // 绘制完成之后在在关闭进度框
            UIUtils.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadingDialog.close();
                    loadingDialog = null;
                }
            }, millis);
        }
    }

    @Override
    public void closeFailDialog() {
        closeFailDialog(1800);
    }

    @Override
    public void closeFailDialog(long millis) {
        if (loadingDialog != null) {
            loadingDialog.loadFailed();
            // 绘制完成之后在在关闭进度框
            UIUtils.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadingDialog.close();
                    loadingDialog = null;
                }
            }, millis);
        }
    }
}
