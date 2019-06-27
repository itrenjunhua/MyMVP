package com.renj.mvpbase.view;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
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
 * 描述：需要访问网络的Activity的基类，同时也是{@link BaseActivity}的子类<br/>
 * 如果定义了泛型参数，那么就会将该泛型的Presenter初始化出来，子类直接使用即可。参数名：<code>mPresenter</code>
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public abstract class BasePresenterActivity<T extends BasePresenter> extends BaseActivity {
    private final String TAG_INFO = "BasePresenterActivity 创建 T extends BasePresenter 失败 => ";

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
     * 在{@link BasePresenterActivity}中重写，初始化页面控制器
     *
     * @param view
     * @return
     */
    @Override
    protected void initRPageStatusController(View view) {
        rPageStatusController = RPageStatusController.get();
        rPageStatusController.bind(this);
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
        }
    }

    @Override
    public void showNetWorkErrorPage(@LoadingStyle int loadingStyle, @IntRange int requestCode) {
        if (loadingStyle == LoadingStyle.LOADING_DIALOG) {
            closeLoadingDialog();
        } else if (loadingStyle == LoadingStyle.LOADING_PAGE) {
            changePageStatus(RPageStatus.NET_WORK);
        }
    }

    @Override
    public void showErrorPage(@LoadingStyle int loadingStyle, @IntRange int requestCode, Throwable e) {
        if (loadingStyle == LoadingStyle.LOADING_DIALOG) {
            closeLoadingDialog();
        } else if (loadingStyle == LoadingStyle.LOADING_PAGE) {
            changePageStatus(RPageStatus.ERROR);
        }
    }

    protected void changePageStatus(@RPageStatus int pageStatus) {
        if (rPageStatusController != null)
            rPageStatusController.changePageStatus(pageStatus);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mPresenter)
            mPresenter.detachView();
    }
}
