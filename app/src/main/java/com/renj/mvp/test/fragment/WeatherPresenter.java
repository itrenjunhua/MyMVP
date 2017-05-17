package com.renj.mvp.test.fragment;

import android.util.Log;

import com.renj.mvp.base.BasePresenter;
import com.renj.mvp.rxjava.ThreadTransformer;

import java.util.Map;

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
public class WeatherPresenter extends BasePresenter<WeatherModel> implements WeatherControl.WeatherPresenter {
    private WeatherFragment weatherFragment;

    @Override
    public void attachView(WeatherControl.WeatherView view) {
        this.weatherFragment = (WeatherFragment) view;
    }

    @Override
    public void detachView(WeatherControl.WeatherView view) {
        this.weatherFragment = null;
    }

    @Override
    public void getData(String path, Map<String, String> queryMap) {
        mModel.getData(path, queryMap)
                .compose(ThreadTransformer.<String>threadChange())
                .compose(weatherFragment.<String>bindToLifecycle())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i("WeatherPresenter", "onSubscribe() --- " + d);
                    }

                    @Override
                    public void onNext(String value) {
                        weatherFragment.setData(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("WeatherPresenter", "---------- " + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.i("WeatherPresenter", "onComplete() ----------");
                    }
                });
    }

    @Override
    public void refreshData(String path, Map<String, String> queryMap) {

    }
}
