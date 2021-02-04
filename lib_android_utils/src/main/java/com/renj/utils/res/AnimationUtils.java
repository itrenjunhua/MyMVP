package com.renj.utils.res;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2020-09-08   10:52
 * <p>
 * 描述：动画工具类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class AnimationUtils {
    /**
     * 控件水平方向平移动画，默认动画时间：300ms，动画结束保持动画之后效果
     *
     * @param view   需要平移的空间
     * @param transX 平移距离
     */
    public static ViewPropertyAnimator animTranslationX(View view, int transX) {
        return animTranslationX(view, transX, 300);
    }

    /**
     * 控件水平方向平移动画，动画结束保持动画之后效果
     *
     * @param view     需要平移的空间
     * @param transX   平移距离
     * @param duration 动画时间
     */
    public static ViewPropertyAnimator animTranslationX(View view, int transX, long duration) {
        if (view == null) return null;

        view.clearAnimation();

        ViewPropertyAnimator animator = view.animate()
                .translationX(transX)
                .setDuration(duration);
        animator.start();
        return animator;
    }

    /**
     * 动态改变控件的宽度，默认动画时间：300ms，动画结束保持动画之后效果
     *
     * @param view        需要改变宽度的控件
     * @param srcWidth    原来宽度
     * @param targetWidth 目标宽度
     */
    public static ValueAnimator animChangeViewWidth(View view, int srcWidth, int targetWidth) {
        return animChangeViewWidth(view, srcWidth, targetWidth, 300);
    }

    /**
     * 动态改变控件的宽度，动画结束保持动画之后效果
     *
     * @param view        需要改变宽度的控件
     * @param srcWidth    原来宽度
     * @param targetWidth 目标宽度
     * @param duration    动画时间
     */
    public static ValueAnimator animChangeViewWidth(final View view, final int srcWidth, int targetWidth, long duration) {
        if (view == null) return null;

        view.clearAnimation();

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        // final int offsetWidth = targetWidth - srcWidth;
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // float animatedFraction = animation.getAnimatedFraction();
                // int width = (int) (offsetWidth * animatedFraction + srcWidth);
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.width = (int) animation.getAnimatedValue();
                view.setLayoutParams(layoutParams);
            }
        });
        valueAnimator.setDuration(duration);
        valueAnimator.start();
        return valueAnimator;
    }

    /**
     * 动态改变控件的高度，默认动画时间：300ms，动画结束保持动画之后效果
     *
     * @param view         需要改变高度的控件
     * @param srcHeight    原来高度
     * @param targetHeight 目标高度
     */
    public static ValueAnimator animChangeViewHeight(View view, int srcHeight, int targetHeight) {
        return animChangeViewHeight(view, srcHeight, targetHeight, 300);
    }

    /**
     * 动态改变控件的高度，动画结束保持动画之后效果
     *
     * @param view         需要改变高度的控件
     * @param srcHeight    原来高度
     * @param targetHeight 目标高度
     * @param duration     动画时间
     */
    public static ValueAnimator animChangeViewHeight(final View view, final int srcHeight, int targetHeight, long duration) {
        if (view == null) return null;

        view.clearAnimation();

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        // final int offsetHeight = targetHeight - srcHeight;
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // float animatedFraction = animation.getAnimatedFraction();
                // int width = (int) (offsetHeight * animatedFraction + srcHeight);
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = (int) animation.getAnimatedValue();;
                view.setLayoutParams(layoutParams);
            }
        });
        valueAnimator.setDuration(duration);
        valueAnimator.start();
        return valueAnimator;
    }

    /**
     * 透明度改变动画，默认动画时间：300ms，动画结束保持动画之后效果
     *
     * @param view        需要改变透明度的控件
     * @param targetValue 最终透明度
     */
    public static ViewPropertyAnimator animAlphaChange(View view, float targetValue) {
        return animAlphaChange(view, targetValue, 300);
    }

    /**
     * 透明度改变动画，动画结束保持动画之后效果
     *
     * @param view        需要改变透明度的控件
     * @param targetValue 最终透明度
     * @param duration    动画时间
     */
    public static ViewPropertyAnimator animAlphaChange(View view, float targetValue, int duration) {
        if (view == null) return null;

        view.clearAnimation();

        ViewPropertyAnimator animator = view.animate()
                .alpha(targetValue)
                .setDuration(duration);
        animator.start();
        return animator;
    }

    /**
     * 给控件添加左右抖动动画，默认动画时间：300ms，默认抖动次数：2，动画结束不保持动画之后效果
     *
     * @param view 需要抖动的控件
     */
    public static Animation shakeAnimation(View view) {
        return shakeAnimation(view, 300);
    }

    /**
     * 给控件添加左右抖动动画，默认抖动次数：2，动画结束不保持动画之后效果
     *
     * @param view     需要抖动的控件
     * @param duration 动画时间
     */
    public static Animation shakeAnimation(View view, int duration) {
        if (view == null) return null;

        view.clearAnimation();

        Animation shakeAnimation = new TranslateAnimation(0, -12, 0, 0);
        shakeAnimation.setInterpolator(new CycleInterpolator(2));
        shakeAnimation.setDuration(duration);
        view.startAnimation(shakeAnimation);
        return shakeAnimation;
    }
}
