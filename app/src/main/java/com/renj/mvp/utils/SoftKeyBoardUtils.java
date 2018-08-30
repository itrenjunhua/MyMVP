package com.renj.mvp.utils;

import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2018-08-30   13:06
 * <p>
 * 描述：操作软键盘工具类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class SoftKeyBoardUtils {
    /**
     * 判断点击事件的控件是否在 {@link EditText} 上
     *
     * @param v     被点击的控件
     * @param event {@link MotionEvent} 对象
     * @return tru：点击的位置在 {@link EditText} 控件的范围  false：点击的位置在非 {@link EditText} 控件范围
     */
    @org.jetbrains.annotations.Contract("null, _ -> false")
    public static boolean isShouldHideInput(View v,@NonNull MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
