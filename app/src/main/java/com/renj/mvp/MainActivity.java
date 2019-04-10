package com.renj.mvp;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.renj.cachelibrary.CacheManageUtils;
import com.renj.mvp.base.view.BaseActivity;
import com.renj.mvp.test.extend.ExtentActivity;
import com.renj.mvp.test.fragment.FragmentActivity;
import com.renj.mvp.test.network.WeatherActivity;
import com.renj.mvp.test.normal.NormalActivity;

import butterknife.BindView;


public class MainActivity extends BaseActivity {
    @BindView(R.id.bt1)
    Button bt1;
    @BindView(R.id.bt2)
    Button bt2;
    @BindView(R.id.bt3)
    Button bt3;
    @BindView(R.id.bt4)
    Button bt4;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        setPageTitle("功能列表");
        setPageBack(false,false,null);

        CacheManageUtils.newInstance().putOnNewThread("aaa","aaavalue: kldfjsdklsdf是非得失老师看来大家退款即可dasfsdd");

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
