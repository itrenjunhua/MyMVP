package com.renj.mvp.test.network;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.renj.mvp.R;
import com.renj.mvp.base.BasePresenterActivity;
import com.renj.mvp.base.dagger.BaseActivityComponent;

import java.util.Map;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-15   18:04
 * <p>
 * 描述：需要访问网络的Activity
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class WeatherActivity extends BasePresenterActivity<WeatherPresenter> implements WeatherControl.WeatherView {
    @InjectView(R.id.textView)
    TextView textView;
    @InjectView(R.id.text_error)
    TextView textError;
    @InjectView(R.id.progressbar)
    ProgressBar progressBar;

    @Inject
    Map<String,String> map;

    @Override
    public int getLayoutId() {
        return R.layout.activity_weather;
    }

    @Override
    public void initData() {
        stateLoading();
        map.put("cityCode", "101040100");
        map.put("weatherType", "1");
        mPresenter.getData("GetMoreWeather/", map);
    }

    @Override
    protected void inject(BaseActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    public void setData(String result) {
        textView.setText(result);
    }

    @Override
    public void stateError() {
        textError.setVisibility(View.VISIBLE);
        textView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void stateContent() {
        textView.setVisibility(View.VISIBLE);
        textError.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void stateLoading() {
        textError.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }
}
