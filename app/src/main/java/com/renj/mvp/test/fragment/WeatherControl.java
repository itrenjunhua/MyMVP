package com.renj.mvp.test.fragment;

import com.renj.mvp.base.BaseControl;

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
    interface WeatherModel extends BaseControl.IModel<String> {
    }

    interface WeatherView extends BaseControl.IView {
        void setData(String result);
    }

    interface WeatherPresenter extends BaseControl.IPresenter<WeatherView> {
    }
}
