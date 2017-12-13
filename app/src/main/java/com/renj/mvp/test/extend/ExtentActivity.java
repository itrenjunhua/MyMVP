package com.renj.mvp.test.extend;

import android.widget.TextView;

import com.renj.mvp.R;
import com.renj.mvp.base.BaseActivity;
import com.renj.mvp.base.dagger.BaseActivityComponent;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-15   18:30
 * <p>
 * 描述：扩展的Activity
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ExtentActivity extends BaseActivity {
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.text2)
    TextView text2;

    @Inject
    Preson preson1;
    @Inject
    Preson preson2;

    @Override
    public int getLayoutId() {
        return R.layout.activity_extend;
    }

    @Override
    public void initData() {
        setTitle("扩展Component");

        preson1.name = "zhangsan";
        preson1.age = 22;
        preson2.name = "lisi";
        preson2.age = 25;

        text1.setText(preson1.toString());
        text2.setText(preson2.toString());
    }

    @Override
    protected void inject(BaseActivityComponent activityComponent) {
        // 使用 Subcomponent 的写法
//        activityComponent.addSub(new ExtendModule()).inject(this);
        // 扩展 Subcomponent 的写法
        DaggerExtendComponent.builder()
                .extendModule(new ExtendModule())
                .build()
                .inject(this);
    }
}
