package com.renj.mvp.mode.http.utils;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.renj.common.utils.Logger;
import com.renj.common.utils.ResUtils;
import com.renj.common.utils.UIUtils;
import com.renj.httplibrary.NetworkException;
import com.renj.mvp.R;
import com.renj.mvp.mode.http.exception.NullDataException;
import com.renj.mvpbase.view.IBaseView;

import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.Response;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2018-07-31   14:10
 * <p>
 * 描述：自定义的 {@link ResourceSubscriber} ，减少重写方法的个数
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public abstract class CustomSubscriber<T> extends ResourceSubscriber<T> {
    @IntRange
    private int requestCode; // 请求code
    private IBaseView mView;

    public CustomSubscriber(@IntRange int requestCode, @NonNull IBaseView view) {
        this.requestCode = requestCode;
        this.mView = view;
    }

    @Override
    public void onNext(T t) {
        onResult(t);
        mView.showContentPage(requestCode, t);
    }

    /**
     * 正确结果回调
     */
    public abstract void onResult(@NonNull T t);

    @Override
    public void onError(Throwable e) {
        // 网络连接异常
        if (e instanceof NetworkException) {
            mView.closeLoadingDialog();
            mView.showNetWorkErrorPage(requestCode);
            UIUtils.showToastSafe(R.string.no_net_work);
            Logger.e("NetWork Exception(网络连接异常) => " + ResUtils.getString(R.string.no_net_work));
        }
        /**
         * 这里还可以对自定义的异常进行处理，这里的异常主要是在 {@link ResponseTransformer#responseResult(Response)} 方法中抛出来的自定义异常
         */
        else if (e instanceof NullDataException) {
            // 如果是自定义的异常，那么可以在这里进行对应的处理
            NullDataException nullDataException = (NullDataException) e;
            mView.showEmptyDataPage(requestCode, nullDataException.getBaseResponseBean());
            Logger.v("Show Empty Page(显示空页面) => requestCode: " + requestCode + "；message: " + "重写了 "
                    + ResponseTransformer.class.getName() + ".onNullDataJudge(T t) throws NullDataException 方法对响应数据进行了 null 判断，并抛出了 "
                    + NullDataException.class.getName() + " 异常，将调用 "
                    + IBaseView.class.getName() + ".showEmptyDataPage(@IntRange int requestCode, @NonNull E e) 方法。");
        } else {
            mView.showErrorPage(requestCode, e);
            Logger.e("Exception Info(异常信息) => " + e);
        }
    }

    @Override
    public void onComplete() {

    }
}
