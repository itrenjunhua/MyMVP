package com.renj.mvp.test.network;

import com.renj.mvp.base.BasePresenter;
import com.renj.mvp.retrofit.ApiServer;
import com.renj.mvp.retrofit.CustomObserver;
import com.renj.mvp.rxjava.MyObservableTransformer;

import java.util.Map;

import javax.inject.Inject;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-15   18:05
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class WeatherPresenter extends BasePresenter<WeatherActivity> implements WeatherControl.WeatherPresenter {

    @Inject
    public WeatherPresenter(ApiServer apiServer) {
        super(apiServer);
    }

    @Override
    public void getData(String path, Map<String, String> queryMap) {

        mApiServer.getWeather(path, queryMap)
                .compose(new MyObservableTransformer<String, WeatherActivity>(mView))
                .subscribe(new CustomObserver<String, WeatherActivity>(mView) {
                    @Override
                    public void onNext(String value) {
                        mView.setData(value);
                        mView.stateContent();
                    }
                });
    }
}
