package com.renj.mvp.test.network;

import com.renj.mvp.base.BaseModel;

import java.util.Map;

import io.reactivex.Observable;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-15   18:03
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class WeatherModel extends BaseModel implements WeatherControl.WeatherModel {
    @Override
    public Observable<String> getData(String path, Map<String, String> queryMap) {
        return mApiServer.getWeather(path,queryMap);
    }

    @Override
    public Observable<String> refreshData(String path, Map<String, String> queryMap) {
        return null;
    }
}
