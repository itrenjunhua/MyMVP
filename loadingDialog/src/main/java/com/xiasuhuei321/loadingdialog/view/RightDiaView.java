package com.xiasuhuei321.loadingdialog.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Luo_xiasuhuei321@163.com on 2016/11/5.
 * desc:
 */

public class RightDiaView extends View {
    private final String TAG = this.getClass().getSimpleName();

    private FinishDrawListener listener;

    private int mWidth = 0;
    private float mPadding = 0f;
    private Paint mPaint;
    private RectF rectF;

    private int line1_x;

    private int line2_x;

    private int times = 0;
    private boolean drawEveryTime = true;
    private int speed = 1;
    private float mSqrt;
    private float mCenter;
    private float mCenterX;
    private float mRadius;
    private float mRadius3;
    private float mStartX2;
    private float mStartY2;

    public RightDiaView(Context context) {
        this(context, null);
    }

    public RightDiaView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RightDiaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPadding = SizeUtils.dip2px(context, 3);
        mSqrt = (float) (mPadding / Math.sqrt(8));
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getMeasuredWidth() > getHeight())
            mWidth = getMeasuredHeight();
        else
            mWidth = getMeasuredWidth();
        rectF = new RectF(mPadding, mPadding, mWidth - mPadding, mWidth - mPadding);
        mCenter = mWidth / 2f;
        mCenterX = mCenter - mWidth / 5f;
        mRadius = mWidth / 2f - mPadding;
        mRadius3 = mRadius / 3f;
        mStartX2 = mCenterX + mRadius3 - mSqrt;
        mStartY2 = mCenter + mRadius3 + mSqrt;
    }

    private void init() {
        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(mPadding);
    }

    int progress = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        if (drawEveryTime)
            drawDynamic(canvas);
        else {
            drawStatic(canvas);
            if (listener != null)
                listener.dispatchFinishEvent(this);
        }
    }

    private int count = 0;

    private void drawDynamic(Canvas canvas) {
        if (progress < 100) {
            progress += speed;
        }

        //根据进度画圆弧
        canvas.drawArc(rectF, 235, 360 * progress / 100, false, mPaint);


        //绘制对勾
        if (progress >= 100) {
            if (line1_x < mRadius3) {
                line1_x += speed;
            }
            //画第一根线
            canvas.drawLine(mCenterX, mCenter, mCenterX + line1_x, mCenter + line1_x, mPaint);
            if (line1_x >= mRadius3) {

                if (mStartX2 + line2_x <= mCenterX + mRadius) {
                    line2_x += speed;
                }
                //画第二根线
                canvas.drawLine(mStartX2, mStartY2,
                        mStartX2 + line2_x, mStartY2 - line2_x, mPaint);

                if (mStartX2 + line2_x > mCenterX + mRadius) {

                    //1.只分发一次绘制完成的事件
                    //2.只在最后一次绘制时分发
                    if (count == 0 && times == 0 && listener != null) {
                        listener.dispatchFinishEvent(this);
                        count++;
                    }

                    times--;
                    if (times >= 0) {
                        reDraw();
                        invalidate();
                    } else {
                        return;
                    }

                }
            }


        }

        invalidate();
    }

    private void drawStatic(Canvas canvas) {
        canvas.drawArc(rectF, 0, 360, false, mPaint);

        canvas.drawLine(mCenterX, mCenter,
                mCenterX + mRadius3, mCenter + mRadius3, mPaint);
        canvas.drawLine(mStartX2, mStartY2,
                mCenterX + mRadius, mCenter - mRadius3, mPaint);
    }


    private void reDraw() {
        line1_x = 0;
        line2_x = 0;
        progress = 0;
    }

    //---------------------------对外提供的api-------------------------//

    /**
     * 设置重复绘制的次数，只在drawEveryTime = true时有效
     *
     * @param times 重复次数，例如设置1，除了第一次绘制还会额外重绘一次
     */
    protected void setRepeatTime(int times) {
        if (drawEveryTime)
            this.times = times;
    }

    /**
     * 动态画出还是直接画出
     */
    protected void setDrawDynamic(boolean drawEveryTime) {
        this.drawEveryTime = drawEveryTime;
    }

    /**
     * 调整绘制的速度，最小值默认为1
     *
     * @param speed 速度
     */
    protected void setSpeed(int speed) {
        if (speed <= 0 && speed >= 3) {
            throw new IllegalArgumentException("how can u set this speed??  " + speed + "  do not " +
                    "use reflect to use this method!u can see the LoadingDialog class for how to" +
                    "set the speed");
        } else {
            this.speed = speed;
        }
    }

    protected void setDrawColor(int color) {
        mPaint.setColor(color);
    }

    public void setOnDrawFinishListener(FinishDrawListener f) {
        this.listener = f;
    }
}

