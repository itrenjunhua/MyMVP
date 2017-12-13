package com.renj.mvp.test.normal;

import android.widget.TextView;

import com.renj.mvp.R;
import com.renj.mvp.base.BaseActivity;
import com.renj.mvp.base.dagger.BaseActivityComponent;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-15   17:34
 * <p>
 * 描述：不需要访问网络的Activity
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class NormalActivity extends BaseActivity {
    @BindView(R.id.textView)
    TextView textView;

    // 使用依赖注入
    @Inject
    List<String> list;

    @Override
    public int getLayoutId() {
        return R.layout.activity_normal;
    }

    @Override
    public void initData() {
        setTitle("不需要访问网络");

        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        list.add("ddd");

        StringBuilder stringBuilder = new StringBuilder();
        for (String s : list) {
            stringBuilder.append(s).append("\n");
        }

        textView.setText(stringBuilder.toString());
    }

    @Override
    protected void inject(BaseActivityComponent activityComponent) {
        activityComponent.inject(this);
    }
}
