package com.renj.mvp.test.fragment;

import android.widget.TextView;

import com.renj.mvp.R;
import com.renj.mvp.base.view.BaseFragment;
import com.renj.mvp.base.dagger.BaseFragmentComponent;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-15   19:34
 * <p>
 * 描述：不需要访问网络Fragment
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class NormalFragment extends BaseFragment {
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
        list.add("111111");
        list.add("222222");
        list.add("333333");
        list.add("444444");

        StringBuilder stringBuilder = new StringBuilder();
        for (String s : list) {
            stringBuilder.append(s).append("\n");
        }

        textView.setText(stringBuilder.toString());
    }

    @Override
    protected void inject(BaseFragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }
}
