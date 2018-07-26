package com.renj.mvp.base;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-09-29   16:39
 * <p>
 * 描述：MVP模式中View顶层接口
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public interface IBaseView {
    int getLayoutId();

    void initData();

    void showLoadingPage();

    void showNetWorkError();

    void showContentPage();

    void showEmptyPage();

    void showErrorPage();
}
