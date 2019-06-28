package com.renj.mvpbase.view;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.renj.common.utils.Logger;
import com.renj.mvpbase.mode.MvpBaseRB;
import com.renj.mvpbase.presenter.BasePresenter;
import com.renj.pagestatuscontroller.IRPageStatusController;
import com.renj.pagestatuscontroller.RPageStatusController;
import com.renj.pagestatuscontroller.annotation.RPageStatus;
import com.renj.pagestatuscontroller.listener.OnRPageEventListener;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-12   10:22
 * <p>
 * 描述：需要访问网络的Fragment的基类，同时也是{@link BaseFragment}的子类<br/>
 * 如果定义了泛型参数，那么就会将该泛型的Presenter初始化出来，子类直接使用即可。参数名：<code>mPresenter</code>
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public abstract class BasePresenterFragment<T extends BasePresenter> extends BaseFragment {
    private final String TAG_INFO = "BasePresenterFragment 创建 T extends BasePresenter 失败 => ";

    private Class<T> mClazz;
    protected T mPresenter;
    protected RPageStatusController rPageStatusController;

    @Override
    protected void initPresenter() {
        // 通过反射获取泛型的Class
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            try {
                mClazz = (Class<T>) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
                mPresenter = mClazz.newInstance();
                if (null != mPresenter)
                    mPresenter.attachView(this);
            } catch (Exception e) {
                e.printStackTrace();
                Logger.e(TAG_INFO + e);
            }
        }
    }

    /**
     * 在{@link BasePresenterFragment}中重写，初始化页面控制器
     *
     * @param view
     * @return
     */
    @Override
    protected View initRPageStatusController(View view) {
        rPageStatusController = RPageStatusController.get();
        rPageStatusController.resetOnRPageEventListener(RPageStatus.ERROR, new OnRPageEventListener() {
            @Override
            public void onViewClick(@NonNull IRPageStatusController iRPageStatusController, int pageStatus, @NonNull Object object, @NonNull View view, int viewId) {
                handlerLoadException(iRPageStatusController, pageStatus, object, view, viewId);
            }
        }).resetOnRPageEventListener(RPageStatus.NET_WORK, new OnRPageEventListener() {
            @Override
            public void onViewClick(@NonNull IRPageStatusController iRPageStatusController, int pageStatus, @NonNull Object object, @NonNull View view, int viewId) {
                handlerLoadException(iRPageStatusController, pageStatus, object, view, viewId);
            }
        }).resetOnRPageEventListener(RPageStatus.EMPTY, new OnRPageEventListener() {
            @Override
            public void onViewClick(@NonNull IRPageStatusController iRPageStatusController, int pageStatus, @NonNull Object object, @NonNull View view, int viewId) {
                handlerLoadException(iRPageStatusController, pageStatus, object, view, viewId);
            }
        }).resetOnRPageEventListener(RPageStatus.NOT_FOUND, new OnRPageEventListener() {
            @Override
            public void onViewClick(@NonNull IRPageStatusController iRPageStatusController, int pageStatus, @NonNull Object object, @NonNull View view, int viewId) {
                handlerLoadException(iRPageStatusController, pageStatus, object, view, viewId);
            }
        });
        return rPageStatusController.bind(this, view);
    }

    /**
     * 处理状态页面的事件
     *
     * @param iRPageStatusController 控制器
     * @param pageStatus             点击事件产生的页面状态
     * @param object                 绑定对象
     * @param view                   点击事件产生的 View
     * @param viewId                 点击事件产生的 View 的 id
     */
    protected void handlerLoadException(IRPageStatusController iRPageStatusController, @RPageStatus int pageStatus, Object object, View view, int viewId) {
    }

    /**
     * 该方法在{@link #showContentPage(int, int, Object)}、{@link #showEmptyDataPage(int, int, MvpBaseRB)}
     * {@link #showNetWorkErrorPage(int, int)}、{@link #showErrorPage(int, int, Throwable)} 时就是使用子类的 {@link RPageStatusController} 中调用，
     * 当 {@link LoadingStyle} 为 {@link LoadingStyle#LOADING_DIALOG}、{@link LoadingStyle#LOADING_PAGE} 状态之外的其他状态调用
     *
     * @param status      当前状态，使用 {@link RPageStatus} 值
     * @param requestCode 请求 code
     * @param object      信息，包括 正确结果数据、异常信息等
     */
    protected void handlerOtherStyle(@RPageStatus int status, @IntRange int requestCode, @Nullable Object object) {
    }

    /**
     * 使用子类的 {@link RPageStatusController} 替换父类的 {@link RPageStatusController}，替换之后再调用<br/>
     * {@link #showContentPage(int, int, Object)}、{@link #showLoadingPage(int, int)}、{@link #showEmptyDataPage(int, int, MvpBaseRB)}
     * {@link #showNetWorkErrorPage(int, int)}、{@link #showErrorPage(int, int, Throwable)} 时就是使用子类的 {@link RPageStatusController}
     *
     * @param rPageStatusController
     */
    protected void replaceSupperPageStatusController(@NonNull RPageStatusController rPageStatusController) {
        this.rPageStatusController = rPageStatusController;
    }

    @Override
    public <E> void showContentPage(@LoadingStyle int loadingStyle, @IntRange int requestCode, @NonNull E e) {
        if (loadingStyle == LoadingStyle.LOADING_DIALOG) {
            closeLoadingDialog();
        } else if (loadingStyle == LoadingStyle.LOADING_PAGE) {
            changePageStatus(RPageStatus.CONTENT);
        } else {
            handlerOtherStyle(loadingStyle, requestCode, e);
        }
    }

    @Override
    public void showLoadingPage(@LoadingStyle int loadingStyle, @IntRange int requestCode) {
        if (loadingStyle == LoadingStyle.LOADING_DIALOG) {
            showLoadingDialog();
        } else if (loadingStyle == LoadingStyle.LOADING_PAGE) {
            changePageStatus(RPageStatus.LOADING);
        }
    }

    @Override
    public <E extends MvpBaseRB> void showEmptyDataPage(@LoadingStyle int loadingStyle, @IntRange int requestCode, @NonNull E e) {
        if (loadingStyle == LoadingStyle.LOADING_DIALOG) {
            closeLoadingDialog();
        } else if (loadingStyle == LoadingStyle.LOADING_PAGE) {
            changePageStatus(RPageStatus.EMPTY);
        } else {
            handlerOtherStyle(loadingStyle, requestCode, e);
        }
    }

    @Override
    public void showNetWorkErrorPage(@LoadingStyle int loadingStyle, @IntRange int requestCode) {
        if (loadingStyle == LoadingStyle.LOADING_DIALOG) {
            closeLoadingDialog();
        } else if (loadingStyle == LoadingStyle.LOADING_PAGE) {
            changePageStatus(RPageStatus.NET_WORK);
        } else {
            handlerOtherStyle(loadingStyle, requestCode, null);
        }
    }

    @Override
    public void showErrorPage(@LoadingStyle int loadingStyle, @IntRange int requestCode, Throwable e) {
        if (loadingStyle == LoadingStyle.LOADING_DIALOG) {
            closeLoadingDialog();
        } else if (loadingStyle == LoadingStyle.LOADING_PAGE) {
            changePageStatus(RPageStatus.ERROR);
        } else {
            handlerOtherStyle(loadingStyle, requestCode, e);
        }
    }

    protected void changePageStatus(@RPageStatus int pageStatus) {
        if (rPageStatusController != null)
            rPageStatusController.changePageStatus(pageStatus);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mPresenter)
            mPresenter.detachView();
    }
}
