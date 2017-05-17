package com.renj.mvp.test.fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import com.renj.mvp.R;
import com.renj.mvp.base.BaseActivity;
import com.renj.mvp.base.dagger.BaseActivityComponent;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-15   19:29
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class FragmentActivity extends BaseActivity {
    @InjectView(R.id.bt1)
    Button bt1;
    @InjectView(R.id.bt2)
    Button bt2;

    @Inject
    NormalFragment normalFragment;
    @Inject
    WeatherFragment weatherFragment;

    private FragmentManager fragmentManager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_fragment;
    }

    @Override
    public void initData() {
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameLayout,normalFragment);
        fragmentTransaction.add(R.id.frameLayout,weatherFragment);
        fragmentTransaction.show(normalFragment).hide(weatherFragment);
        fragmentTransaction.commit();

        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
    }

    @Override
    protected void inject(BaseActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void handlerClick(View v, int vId) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (vId) {
            case R.id.bt1:
                fragmentTransaction.show(normalFragment).hide(weatherFragment);
                break;
            case R.id.bt2:
                fragmentTransaction.show(weatherFragment).hide(normalFragment);
                break;
            default:
                break;
        }
        fragmentTransaction.commit();
    }
}
