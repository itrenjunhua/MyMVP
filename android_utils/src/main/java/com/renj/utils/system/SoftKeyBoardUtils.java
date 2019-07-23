package com.renj.utils.system;

import android.app.Activity;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：itrenjunhua@163.com
 * <p>
 * 创建时间：2018-03-30   13:06
 * <p>
 * 描述：操作软键盘工具类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class SoftKeyBoardUtils {
    /**
     * 判断软键盘是否显示并计算软键盘的高度
     *
     * @param activity
     * @param listener
     */
    public static void observeSoftKeyboard(@NonNull Activity activity, final OnSoftKeyboardChangeListener listener) {
        final View decorView = activity.getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    int previousKeyboardHeight = -1;

                    @Override
                    public void onGlobalLayout() {
                        Rect rect = new Rect();
                        decorView.getWindowVisibleDisplayFrame(rect);
                        int displayHeight = rect.bottom - rect.top;
                        int height = decorView.getHeight();
                        int keyboardHeight = height - displayHeight;
                        if (previousKeyboardHeight != keyboardHeight && listener != null) {
                            boolean hide = (double) displayHeight / height > 0.8;
                            listener.onSoftKeyBoardChange(keyboardHeight, !hide);
                        }

                        previousKeyboardHeight = height;

                    }
                });
    }

    /**
     * 回调接口
     *
     * @author Administrator
     */
    public interface OnSoftKeyboardChangeListener {
        /**
         * 回调函数
         *
         * @param softKeyboardHeight 软键盘的高度
         * @param visible            软键盘是否已经显示
         */
        void onSoftKeyBoardChange(int softKeyboardHeight, boolean visible);
    }
}
