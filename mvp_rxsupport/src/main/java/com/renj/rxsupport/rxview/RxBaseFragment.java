package com.renj.rxsupport.rxview;

import android.support.annotation.NonNull;

import com.renj.mvpbase.view.BaseFragment;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-11   18:39
 * <p>
 * 描述：MVP 模式中基于 RxJava 的 View，对生命周期进行绑定
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public abstract class RxBaseFragment extends BaseFragment {
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();

    /**
     * 将 {@link Disposable} 增加到 {@link CompositeDisposable} 中，对生命周期进行控制
     *
     * @param disposable 需要增加的 {@link Disposable}
     */
    public void addDisposable(@NonNull Disposable disposable) {
        if (compositeDisposable == null)
            compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        // 清除事件
        if (compositeDisposable != null)
            compositeDisposable.clear();

        super.onDestroy();
    }
}
