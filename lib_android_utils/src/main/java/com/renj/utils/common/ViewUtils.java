package com.renj.utils.common;

import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：itrenjunhua@163.com
 * <p>
 * 创建时间：2018-11-13   11:04
 * <p>
 * 描述：View 相关工具类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ViewUtils {

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;

    /**
     * 是否间隔指定时间，默认 1s
     *
     * @return true：是 false：没有
     */
    public static boolean isFastClick() {
        return isFastClick(MIN_CLICK_DELAY_TIME);
    }

    /**
     * 是否间隔指定时间
     *
     * @param time 指定间隔时长，单位：ms
     * @return true：是 false：没有
     */
    public static boolean isFastClick(long time) {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= time) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

    /**
     * 显示多个控件
     *
     * @param views 控件，可变参数
     */
    public static void showView(@NonNull View... views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * INVISIBLE 多个控件
     *
     * @param views 控件，可变参数
     */
    public static void invilibleView(@NonNull View... views) {
        for (View view : views) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * GONE 多个控件
     *
     * @param views 控件，可变参数
     */
    public static void goneView(@NonNull View... views) {
        for (View view : views) {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * 把自身从父View中移除
     *
     * @param view 需要移出的View
     */
    public static void removeSelfFromParent(@NonNull View view) {
        if (view != null) {
            ViewParent parent = view.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) parent;
                group.removeView(view);
            }
        }
    }

    /**
     * 请求View树重新布局，用于解决中层View有布局状态而导致上层View状态断裂
     *
     * @param view  需要重新布局的View
     * @param isAll 是否将所有上级控件进行重新布局 true：是
     */
    public static void requestLayoutParent(@NonNull View view, @NonNull boolean isAll) {
        ViewParent parent = view.getParent();
        while (parent != null && parent instanceof View) {
            if (!parent.isLayoutRequested()) {
                parent.requestLayout();
                if (!isAll) {
                    break;
                }
            }
            parent = parent.getParent();
        }
    }

    /**
     * 判断触摸事件是否落在该View上
     *
     * @param ev MotionEvent 对象
     * @param v  需要判断的View
     * @return true：在该View上 false：没有在该View上
     */
    public static boolean isTouchInView(@NonNull MotionEvent ev, @NonNull View v) {
        int[] vLoc = new int[2];
        v.getLocationOnScreen(vLoc);
        float motionX = ev.getRawX();
        float motionY = ev.getRawY();
        return motionX >= vLoc[0] && motionX <=
                (vLoc[0] + v.getWidth()) && motionY >= vLoc[1] && motionY <= (vLoc[1] + v.getHeight());
    }

    /**
     * 判断点击事件的控件是否在 {@link EditText} 上
     *
     * @param v     被点击的控件
     * @param event {@link MotionEvent} 对象
     * @return tru：点击的位置在 {@link EditText} 控件的范围  false：点击的位置在非 {@link EditText} 控件范围
     */
    @org.jetbrains.annotations.Contract("null, _ -> false")
    public static boolean isShouldHideInput(View v, @NonNull MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            // 获取输入框当前的location位置
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

    /**
     * findViewById的泛型封装，减少强转代码
     *
     * @param layout 根布局对象
     * @param id     控件id
     * @param <T>    T extends View
     * @return id对应的控件
     */
    public static <T extends View> T findViewById(@NonNull View layout, @NonNull int id) {
        return (T) layout.findViewById(id);
    }
}
