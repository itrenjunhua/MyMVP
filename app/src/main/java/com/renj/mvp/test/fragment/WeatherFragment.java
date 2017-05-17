package com.renj.mvp.test.fragment;

import android.widget.TextView;

import com.renj.mvp.R;
import com.renj.mvp.base.BasePresenterFragment;
import com.renj.mvp.base.dagger.BaseFragmentComponent;

import java.util.Map;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-15   19:47
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class WeatherFragment extends BasePresenterFragment<WeatherPresenter> implements WeatherControl.WeatherView {
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
    public void setData(String result) {
        textView.setText(result);
    }

    @Override
    protected void inject(BaseFragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }
}
