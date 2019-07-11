package com.renj.mvpbase.view;

import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;


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
    /**
     * 获取布局文件资源id
     *
     * @return
     */
    @LayoutRes
    int getLayoutId();

    /**
     * 初始化数据
     */
    void initData();

    /**
     * 显示内容页面
     *
     * @param loadingStyle 请求样式 {@link LoadingStyle}
     * @param requestCode  请求code
     * @param e            表示返回空数据的是具体的哪个响应实体类，主要作用在一个页面多个请求时进行区分
     * @param <E>
     */
    <E> void showContentPage(@LoadingStyle int loadingStyle, @IntRange int requestCode, @NonNull E e);

    /**
     * 显示正在加载中页面
     *
     * @param loadingStyle 请求样式 {@link LoadingStyle}
     * @param requestCode  请求code
     */
    void showLoadingPage(@LoadingStyle int loadingStyle, @IntRange int requestCode);

    /**
     * 显示空数据页面
     *
     * @param loadingStyle 请求样式 {@link LoadingStyle}
     * @param requestCode  请求code
     * @param e            表示返回空数据的是具体的哪个响应实体类，主要作用在一个页面多个请求时进行区分
     * @param <E>
     */
    <E> void showEmptyDataPage(@LoadingStyle int loadingStyle, @IntRange int requestCode, @NonNull E e);

    /**
     * 显示网路连接异常页面
     *
     * @param loadingStyle 请求样式 {@link LoadingStyle}
     * @param requestCode  请求code
     */
    void showNetWorkErrorPage(@LoadingStyle int loadingStyle, @IntRange int requestCode);

    /**
     * 显示错误页面<br/>
     *
     * @param loadingStyle 请求样式 {@link LoadingStyle}
     * @param requestCode  请求code
     * @param e            错误信息
     */
    void showErrorPage(@LoadingStyle int loadingStyle, @IntRange int requestCode, Throwable e);

    /****************  加载对话框处理 ***********/

    /**
     * 显示加载对话框，使用默认文案 "加载中..."、"加载成功"、"加载失败"
     */
    void showLoadingDialog();

    /**
     * 显示加载对话框，指定加载中状态显示文案
     *
     * @param loadingMsg 加载中状态显示文案
     */
    void showLoadingDialog(@NonNull String loadingMsg);


    /**
     * 显示加载对话框，指定加载中状态、加载成功状态显示文案、加载失败状态
     *
     * @param loadingMsg 加载中状态显示文案
     * @param succeedMsg 加载成功状态显示文案
     * @param failMsg    加载失败状态显示文案
     */
    void showLoadingDialog(@NonNull String loadingMsg, @NonNull String succeedMsg, @NonNull String failMsg);

    /**
     * 关闭加载对话框
     */
    void closeLoadingDialog();

    /**
     * 显示成功状态，并关闭
     */
    void closeSucceedDialog();

    /**
     * 显示成功状态，并关闭
     *
     * @param millis 延迟时间
     */
    void closeSucceedDialog(long millis);

    /**
     * 显示失败状态，并关闭
     */
    void closeFailDialog();

    /**
     * 显示失败状态，并关闭
     *
     * @param millis 延迟时间
     */
    void closeFailDialog(long millis);
}
