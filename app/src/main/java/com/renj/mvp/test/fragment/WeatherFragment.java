package com.renj.mvp.test.fragment;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.renj.mvp.R;
import com.renj.mvp.base.dagger.BaseFragmentComponent;
import com.renj.mvp.base.view.BasePresenterFragment;

import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

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
    private final int REQUEST_CODE = 0;

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.text_error)
    TextView textError;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    @Inject
    Map<String, String> map;

    @Override
    public int getLayoutId() {
        return R.layout.activity_weather;
    }

    @Override
    public void initData() {
        showLoadingPage(REQUEST_CODE);
        map.put("cityCode", "101040100");
        map.put("weatherType", "1");
        mPresenter.getData(REQUEST_CODE, "GetMoreWeather/", map);
    }

    @Override
    public void setData(@IntRange int requestCode, String result) {
        textView.setText(result);
    }

    @Override
    protected void inject(BaseFragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    public void showErrorPage(@IntRange int requestCode, Throwable e) {
        textError.setVisibility(View.VISIBLE);
        textView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public <E> void showContentPage(@IntRange int requestCode, @NonNull E e) {
        textView.setVisibility(View.VISIBLE);
        textError.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingPage(@IntRange int requestCode) {
        textError.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }
}
