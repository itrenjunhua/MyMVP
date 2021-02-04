package com.renj.mvpbase.view;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.renj.mvpbase.R;
import com.renj.utils.common.ActivityManager;
import com.renj.utils.common.UIUtils;
import com.renj.utils.res.ResUtils;
import com.renj.utils.res.ViewUtils;
import com.renj.utils.system.SoftKeyBoardUtils;
import com.renj.view.TitleView;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;


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
public abstract class BaseActivity extends AppCompatActivity implements IBaseView, View.OnClickListener {
    // 公共标题控件
    protected TitleView mTitleView;
    // 内容控件
    protected View mContentView;

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
        setContentView(R.layout.activity_base);
        ActivityManager.addActivity(this);
        initTitle();
        FrameLayout vsContent = findViewById(R.id.view_content);
        mContentView = LayoutInflater.from(this).inflate(getLayoutId(), vsContent, true);
        initRPageStatusController(mContentView);
        initView(mContentView);
        initPresenter();
        initListener();
        initData();
    }

    private void initTitle() {
        // 初始化基本布局ViewStub
        mTitleView = findViewById(R.id.view_title_bar);
        mTitleView.setVisibility(isShowTitleBar() ? View.VISIBLE : View.GONE);
        mTitleView.setOnBackViewClickListener(new TitleView.OnBackViewClickListener() {
            @Override
            public void onBackClick() {
                onTitleViewBack();
            }
        });
    }

    @Override
    public void initListener() {
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
                SoftKeyBoardUtils.hideSoftInput(this, v);
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
     *
     * @return true：需要显示；false：不需要显示
     */
    protected boolean isShowTitleBar() {
        return true;
    }

    /**
     * 提供方法设置是否显示标题栏，优先级高于 {@link #isShowTitleBar()}
     *
     * @param showTitleBar true：显示  false：不显示
     */
    public void setShowTitleBar(boolean showTitleBar) {
        if (mTitleView != null) {
            mTitleView.setVisibility(showTitleBar ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 需要修改标题返回按钮的功能时，重写该方法即可<br/>
     */
    protected void onTitleViewBack() {
        finish();
    }

    /**
     * 设置标题内容，前提是 {@link #isShowTitleBar()} 方法返回 true<br/>
     *
     * @param titleStringId 标题显示内容资源文件
     * @see #setPageTitle(int)
     */
    public void setPageTitle(@StringRes int titleStringId) {
        setPageTitle(getResources().getString(titleStringId));
    }

    /**
     * 设置标题内容，前提是 {@link #isShowTitleBar()} 方法返回 true<br/>
     *
     * @param titleMsg 标题显示内容
     * @see #setPageTitle(int)
     */
    public void setPageTitle(@NonNull String titleMsg) {
        if (mTitleView != null) {
            mTitleView.setTitleContent(titleMsg);
        }
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
    public <E> void showContentPage(@LoadingStyle int loadingStyle, @NonNull E e) {
    }

    @Override
    public void showLoadingPage(@LoadingStyle int loadingStyle) {
    }

    @Override
    public <E> void showEmptyDataPage(@LoadingStyle int loadingStyle, @Nullable E e) {
    }

    @Override
    public void showNetWorkErrorPage(@LoadingStyle int loadingStyle) {
    }

    @Override
    public void showErrorPage(@LoadingStyle int loadingStyle, Throwable e) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeLoadingDialog();
        ActivityManager.removeActivity(this);
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
