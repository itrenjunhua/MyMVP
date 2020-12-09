package com.renj.imageloaderlibrary.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.NonNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2016-12-28    11:30
 * <p/>
 * 描述：
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public class BitmapUtils {
    /**
     * 剪切图片
     *
     * @param source 原图
     * @return
     */
    public static Bitmap clipImage(@NonNull Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
        return result;
    }

    /**
     * 变为圆角矩形图片
     *
     * @param source 原图
     * @param radius 圆角大小
     * @return
     */
    public static Bitmap makeRoundRect(@NonNull Bitmap source, int radius) {
        return makeRoundRect(source, radius, radius);
    }

    /**
     * 变为圆角矩形图片
     *
     * @param source  原图
     * @param radiusX X方向圆角大小
     * @param radiusY Y方向圆角大小
     * @return
     */
    public static Bitmap makeRoundRect(@NonNull Bitmap source, int radiusX, int radiusY) {
        int width = source.getWidth();
        int height = source.getHeight();
        int left = 0, top = 0, right = width, bottom = height;

        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        RectF rectF = new RectF(left, top, right, bottom);

        canvas.drawRoundRect(rectF, radiusX, radiusY, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, left, top, paint);

        return result;
    }

    /**
     * 变为圆形图片
     *
     * @param source 原图
     * @return
     */
    public static Bitmap makeCircleImage(@NonNull Bitmap source) {
        int width = source.getWidth();
        int height = source.getHeight();
        int centerX = width / 2;
        int centerY = height / 2;
        int radius = Math.min(width, height) / 2;

        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Canvas canvas = new Canvas(result);
        canvas.drawCircle(centerX, centerY, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);

        return result;
    }

    /**
     * 获得带倒影的图片方法
     *
     * @param source 源Bitmap
     * @return 带倒影的Bitmap
     */
    public static Bitmap createReflectionBitmap(@NonNull Bitmap source) {
        final int reflectionGap = 4;
        int width = source.getWidth();
        int height = source.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(source, 0, height / 2, width, height / 2, matrix, false);
        Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 2), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(source, 0, 0, null);
        Paint defaultPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);
        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, source.getHeight(), 0, bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);

        return bitmapWithReflection;
    }

    /**
     * 压缩图片到指定大小
     *
     * @param source 原图
     * @param size   压缩后的大小，单位 kb
     * @return 压缩后的Bitmap
     */
    public static Bitmap compressImage(@NonNull Bitmap source, int size) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        source.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > size) { // 循环判断如果压缩后图片是否大于指定大小,大于继续压缩
            baos.reset();// 重置baos即清空baos
            source.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        
        return bitmap;
    }

    /**
     * 压缩图片到100kb
     *
     * @param source 原图
     * @return 压缩后的Bitmap
     */
    public static Bitmap compressImage(@NonNull Bitmap source) {
        return compressImage(source, 100);
    }


    /**
     * 将彩色图转换为灰度图
     *
     * @param source 源Bitmap
     * @return 返回转换好的位图
     */
    public static Bitmap convertGreyImg(@NonNull Bitmap source) {
        int width = source.getWidth(); // 获取位图的宽
        int height = source.getHeight(); // 获取位图的高

        int[] pixels = new int[width * height]; // 通过位图的大小创建像素点数组

        source.getPixels(pixels, 0, width, 0, 0, width, height);
        int alpha = 0xFF << 24;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int grey = pixels[width * i + j];

                int red = ((grey & 0x00FF0000) >> 16);
                int green = ((grey & 0x0000FF00) >> 8);
                int blue = (grey & 0x000000FF);

                grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                grey = alpha | (grey << 16) | (grey << 8) | grey;
                pixels[width * i + j] = grey;
            }
        }
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        result.setPixels(pixels, 0, width, 0, 0, width, height);
        
        return result;
    }

    /**
     * 图片的缩放方法
     *
     * @param source    ：源图片资源
     * @param newWidth  ：缩放后宽度
     * @param newHeight ：缩放后高度
     */
    public static Bitmap scale(@NonNull Bitmap source, double newWidth, double newHeight) {
        // 记录source的宽高
        float width = source.getWidth();
        float height = source.getHeight();
        // 创建一个matrix容器
        Matrix matrix = new Matrix();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 开始缩放
        matrix.postScale(scaleWidth, scaleHeight);
        // 创建缩放后的图片
        Bitmap result = Bitmap.createBitmap(source, 0, 0, (int) width, (int) height, matrix, true);
        
        return result;
    }

    /**
     * 图片的缩放方法
     *
     * @param source      ：源图片资源
     * @param scaleMatrix ：缩放规则
     */
    public static Bitmap scale(@NonNull Bitmap source, @NonNull Matrix scaleMatrix) {
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), scaleMatrix, true);
    }

    /**
     * 图片的缩放方法
     *
     * @param source ：源图片资源
     * @param scaleX ：横向缩放比例
     * @param scaleY ：纵向缩放比例
     */
    public static Bitmap scale(@NonNull Bitmap source, float scaleX, float scaleY) {
        Matrix matrix = new Matrix();
        matrix.postScale(scaleX, scaleY);
        Bitmap result = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        
        return result;
    }

    /**
     * 图片的缩放方法
     *
     * @param source ：源图片资源
     * @param scale  ：缩放比例
     */
    public static Bitmap scale(@NonNull Bitmap source, float scale) {
        return scale(source, scale, scale);
    }

    /**
     * 旋转图片
     *
     * @param source 要旋转的图片
     * @param angle  旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotate(@NonNull Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        Bitmap result = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        
        return result;
    }

    /**
     * 旋转图片
     *
     * @param source 要旋转的图片
     * @param angle  旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotate(@NonNull Bitmap source, float angle, float pivotX, float pivotY) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle, pivotX, pivotY);
        Bitmap result = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        
        return result;
    }

    /**
     * 水平翻转处理
     *
     * @param source 原图
     * @return 水平翻转后的图片
     */
    public static Bitmap reverseByHorizontal(@NonNull Bitmap source) {
        Matrix matrix = new Matrix();
        matrix.preScale(-1, 1);
        Bitmap result = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, false);
        
        return result;
    }

    /**
     * 垂直翻转处理
     *
     * @param source 原图
     * @return 垂直翻转后的图片
     */
    public static Bitmap reverseByVertical(@NonNull Bitmap source) {
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);
        Bitmap result = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, false);
        
        return result;
    }

    /**
     * 更改图片色系，变亮或变暗
     *
     * @param source 原图
     * @param delta  图片的亮暗程度值，越小图片会越亮，取值范围(0,24)
     * @return
     */
    public static Bitmap adjustTone(@NonNull Bitmap source, int delta) {
        if (delta >= 24 || delta <= 0) {
            return null;
        }
        // 设置高斯矩阵
        int[] gauss = new int[]{1, 2, 1, 2, 4, 2, 1, 2, 1};
        int width = source.getWidth();
        int height = source.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        int pixR = 0;
        int pixG = 0;
        int pixB = 0;
        int pixColor = 0;
        int newR = 0;
        int newG = 0;
        int newB = 0;
        int idx = 0;
        int[] pixels = new int[width * height];

        source.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 1, length = height - 1; i < length; i++) {
            for (int k = 1, len = width - 1; k < len; k++) {
                idx = 0;
                for (int m = -1; m <= 1; m++) {
                    for (int n = -1; n <= 1; n++) {
                        pixColor = pixels[(i + m) * width + k + n];
                        pixR = Color.red(pixColor);
                        pixG = Color.green(pixColor);
                        pixB = Color.blue(pixColor);

                        newR += (pixR * gauss[idx]);
                        newG += (pixG * gauss[idx]);
                        newB += (pixB * gauss[idx]);
                        idx++;
                    }
                }
                newR /= delta;
                newG /= delta;
                newB /= delta;
                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));
                pixels[i * width + k] = Color.argb(255, newR, newG, newB);
                newR = 0;
                newG = 0;
                newB = 0;
            }
        }
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

        return bitmap;
    }
}
