package com.renj.home.debug;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.renj.arouter.ARouterPath;
import com.renj.arouter.ARouterUtils;
import com.renj.home.R;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-07-29   13:56
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class HomeMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug_home_main_activity);

        Fragment fragment = ARouterUtils.getFragment(ARouterPath.PATH_HOME_FRAGMENT_HOME);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.library_content, fragment)
                .commit();
    }
}
