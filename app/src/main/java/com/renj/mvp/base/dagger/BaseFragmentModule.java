package com.renj.mvp.base.dagger;

import com.renj.mvp.annotation.ScopFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dagger.Module;
import dagger.Provides;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-12   9:55
 * <p>
 * 描述：{@link com.renj.mvp.base.BaseActivity}的Module，定义常用的类<br/>
 * <b>如果有其他的比较特别的，需要另外扩展Module</b>
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
@Module
public class BaseFragmentModule {
    @Provides
    @ScopFragment
    public List<String> provideList() {
        return new ArrayList<String>();
    }

    @Provides
    @ScopFragment
    public Map<String, String> provideMap() {
        return new HashMap<>();
    }
}
