package com.renj.mvp.test.extend;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.renj.mvp.R;
import com.renj.mvp.base.view.BaseActivity;

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
    Person person1;
    @Inject
    Person person2;

    @Override
    public int getLayoutId() {
        return R.layout.activity_extend;
    }

    @Override
    public void initData() {
        setPageTitle("扩展Component");
        setTitleBarRightViewText("右边", new OnTitleRightClickListener() {
            @Override
            public void onRightViewClick(View view) {
                Toast.makeText(ExtentActivity.this, "点击右边文字", Toast.LENGTH_SHORT).show();
            }
        });


        person1.name = "zhangsan";
        person1.age = 22;
        person2.name = "lisi";
        person2.age = 25;

        text1.setText(person1.toString());
        text2.setText(person2.toString());
    }
}
