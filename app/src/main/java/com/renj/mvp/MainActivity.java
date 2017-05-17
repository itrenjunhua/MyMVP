package com.renj.mvp;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.renj.mvp.base.BaseActivity;
import com.renj.mvp.base.dagger.BaseActivityComponent;
import com.renj.mvp.test.extend.ExtentActivity;
import com.renj.mvp.test.fragment.FragmentActivity;
import com.renj.mvp.test.network.WeatherActivity;
import com.renj.mvp.test.normal.NormalActivity;

import butterknife.InjectView;

public class MainActivity extends BaseActivity {
    @InjectView(R.id.bt1)
    Button bt1;
    @InjectView(R.id.bt2)
    Button bt2;
    @InjectView(R.id.bt3)
    Button bt3;
    @InjectView(R.id.bt4)
    Button bt4;

    @Override
    protected void inject(BaseActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
    }

    @Override
    protected void handlerClick(View v, int vId) {
        Intent intent;
        switch (vId) {
            case R.id.bt1:
                intent = new Intent(this, NormalActivity.class);
                startActivity(intent);
                break;
            case R.id.bt2:
                intent = new Intent(this, WeatherActivity.class);
                startActivity(intent);
                break;
            case R.id.bt3:
                intent = new Intent(this, ExtentActivity.class);
                startActivity(intent);
                break;
            case R.id.bt4:
                intent = new Intent(this, FragmentActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

}
