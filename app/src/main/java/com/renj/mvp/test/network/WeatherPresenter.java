package com.renj.mvp.test.network;

import com.renj.mvp.base.BasePresenter;
import com.renj.mvp.retrofit.ApiServer;
import com.renj.mvp.rxjava.ThreadTransformer;
import com.renj.mvp.utils.MyLogger;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

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

        mApiServer.getWeather(path,queryMap)
                .compose(ThreadTransformer.<String>threadChange())
                .compose(mView.<String>bindToLifecycle())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        MyLogger.i("onSubscribe() --- " + d);
                    }

                    @Override
                    public void onNext(String value) {
                        mView.setData(value);
                        mView.stateContent();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.stateError();
                        MyLogger.e("---------- " + e);
                    }

                    @Override
                    public void onComplete() {
                        MyLogger.i("onComplete() ----------");
                    }
                });
    }
}
