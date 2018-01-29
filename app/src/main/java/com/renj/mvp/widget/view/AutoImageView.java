package com.renj.mvp.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.renj.mvp.R;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2018-01-29   17:52
 * <p>
 * 描述：通过宽高比例自动适配的图片控件
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class AutoImageView extends AppCompatImageView {
    // 自动适配的类型，0：宽适配 1：高适配
    private int auto_type = 1;
    private int auto_width;
    private int auto_height;


    public AutoImageView(Context context) {
        this(context, null, 0);
    }

    public AutoImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoImageView);
        auto_height = typedArray.getInt(R.styleable.AutoImageView_auto_height, 0);
        auto_width = typedArray.getInt(R.styleable.AutoImageView_auto_width, 0);
        auto_type = typedArray.getInt(R.styleable.AutoImageView_auto_type, 1);
        typedArray.recycle();
        // Logger.i("auto_width : " + auto_width + " --- auto_height : " + auto_height + "  --- auto_type : " + auto_type);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
                getDefaultSize(0, heightMeasureSpec));

        double viewWidth = getMeasuredWidth();
        double viewHeight = getMeasuredHeight();
        switch (auto_type) {
            case 0: // 动态计算出控件宽
                viewWidth = viewHeight * (((double) auto_width) / ((double) auto_height));
                break;
            case 1: // 动态计算出控件高
                viewHeight = viewWidth * (((double) auto_height) / ((double) auto_width));
                break;
            default:
                break;
        }

        // Logger.i("width : " + viewWidth + " --- height : " + viewHeight);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec((int) viewWidth, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) viewHeight, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
