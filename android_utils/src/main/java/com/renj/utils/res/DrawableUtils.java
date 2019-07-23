package com.renj.utils.res;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;

import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：itrenjunhua@163.com
 * <p>
 * 创建时间：2018-11-14   10:48
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class DrawableUtils {
    /**
     * 创建一个 {@link GradientDrawable} 图片
     *
     * @param contentColor 内部填充颜色
     * @param strokeWidth  边框宽度
     * @param strokeColor  描边颜色
     * @param radius       圆角
     * @return {@link GradientDrawable} 图片
     */
    public static GradientDrawable createDrawable(int contentColor, int strokeWidth, int strokeColor, int radius) {
        GradientDrawable drawable = new GradientDrawable(); // 生成Shape
        drawable.setGradientType(GradientDrawable.RECTANGLE); // 设置矩形
        drawable.setColor(contentColor);// 内容区域的颜色
        drawable.setStroke(strokeWidth, strokeColor); // 四周描边,描边后四角真正为圆角，不会出现黑色阴影。如果父窗体是可以滑动的，需要把父View设置setScrollCache(false)
        drawable.setCornerRadius(radius); // 设置四角都为圆角
        return drawable;
    }

    /**
     * 获取 {@link Drawable} 图片的大小
     *
     * @param drawable {@link Drawable} 对象
     * @return {@link Drawable} 大小
     */
    public static int getDrawableSize(Drawable drawable) {
        if (drawable == null) {
            return 0;
        }
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        } else {
            return bitmap.getRowBytes() * bitmap.getHeight();
        }
    }

    /**
     * 动态创建状态选择器
     *
     * @param stateValues {@link StateValue} 对象集合
     * @return {@link StateListDrawable} 对象
     */
    public static StateListDrawable createSelector(List<StateValue> stateValues) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        if (stateValues == null || stateValues.isEmpty()) return stateListDrawable;

        for (StateValue stateValue : stateValues) {
            stateListDrawable.addState(stateValue.ints, stateValue.drawable);
        }
        return stateListDrawable;
    }

    /**
     * 状态选择器 状态数组-对应图片值 封装对象
     */
    public static class StateValue {
        /**
         * 状态值数组 如：new int[]{android.R.attr.checked,android.R.attr.process}
         */
        public int[] ints;
        /**
         * 指定状态下的图片
         */
        public Drawable drawable;
    }
}
