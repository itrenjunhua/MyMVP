package com.renj.mvp.test.network;

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

    @Inject
    Map<String,String> map;

    @Override
    public int getLayoutId() {
        return R.layout.activity_weather;
    }

    @Override
    public void initData() {
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
}
