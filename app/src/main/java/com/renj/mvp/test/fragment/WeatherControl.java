package com.renj.mvp.test.fragment;

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

    interface WeatherView {
        void setData(String result);
    }

    interface WeatherPresenter {
        void getData(String path, Map<String, String> queryMap);
    }
}
