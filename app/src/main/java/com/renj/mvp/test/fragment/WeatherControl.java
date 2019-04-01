package com.renj.mvp.test.fragment;

import android.support.annotation.IntRange;

import com.renj.mvp.base.presenter.IBasePresenter;
import com.renj.mvp.base.view.IBaseView;

import java.util.Map;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-15   18:00
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public interface WeatherControl {

    interface WeatherView extends IBaseView {
        void setData(@IntRange int requestCode, String result);
    }

    interface WeatherPresenter extends IBasePresenter<WeatherView> {
        void getData(@IntRange int requestCode, String path, Map<String, String> queryMap);
    }
}
