package com.renj.mvp.test.network;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.renj.mvp.R;
import com.renj.mvp.base.view.BasePresenterActivity;

import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

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
        setPageTitle("访问网络");
        TextView rightView = (TextView) setTitleBarRightView(R.layout.default_title_bar_right_text);
        rightView.setText("自定义布局");
        rightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WeatherActivity.this, "自定义右边布局", Toast.LENGTH_SHORT).show();
            }
        });

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
