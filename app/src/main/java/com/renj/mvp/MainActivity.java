package com.renj.mvp;

import com.renj.daggersupport.DaggerSupportActivity;

public class MainActivity extends DaggerSupportActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
    }
}
