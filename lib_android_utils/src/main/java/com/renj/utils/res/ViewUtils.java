package com.renj.utils.res;

import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.widget.EditText;
import android.widget.TextView;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2018-04-18   16:17
 * <p>
 * 描述：获取控件内容工具类 如：获取EditText、TextView的内容等
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ViewUtils {
    /**
     * 从 {@link EditText} 控件中获取内容
     *
     * @param editText {@link EditText} 控件
     * @return {@link EditText} 控件中的内容
     */
    @NonNull
    public static String getText(@NonNull EditText editText) {
        return editText.getText().toString().trim();
    }

    /**
     * 从 {@link TextView} 控件中获取内容
     *
     * @param textView {@link TextView} 控件
     * @return {@link TextView} 控件中的内容
     */
    @NonNull
    public static String getText(@NonNull TextView textView) {
        return textView.getText().toString().trim();
    }

    /**
     * 设置 {@link TextView} 文字颜色
     *
     * @param color     文字颜色
     * @param textViews @link TextView} 控件
     */
    public static void setTextColor(@ColorInt int color, @NonNull TextView... textViews) {
        for (TextView textView : textViews) {
            textView.setTextColor(color);
        }
    }

    /**
     * 给TextView增加删除线
     *
     * @param textView
     */
    public static void setStrikeThrough(TextView textView) {
        if (textView != null) {
            textView.getPaint().setFlags(Paint.ANTI_ALIAS_FLAG | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    /**
     * 给TextView加粗或者不加粗
     *
     * @param textView
     * @param bold     是否加粗  true：加粗；  false：不加粗
     */
    public static void setTextBold(TextView textView, boolean bold) {
        if (textView != null) {
            TextPaint tp = textView.getPaint();
            if (bold)
                tp.setFakeBoldText(true);
            else
                tp.setFakeBoldText(false);
        }
    }
}
