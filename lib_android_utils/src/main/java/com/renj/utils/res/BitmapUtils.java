package com.renj.utils.res;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Base64;

import com.renj.utils.check.CheckUtils;
import com.renj.utils.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：itrenjunhua@163.com
 * <p>
 * 创建时间：2018-11-13   15:52
 * <p>
 * 描述：操作 Bitmap 相关方法
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class BitmapUtils {

    /**
     * 高效decode图片
     *
     * @param bitmapPath   原始图片路径
     * @param targetWidth  目标宽
     * @param targetHeight 目标高
     * @return Bitmap
     */
    public static Bitmap decodeBitmap(@NonNull String bitmapPath, int targetWidth, int targetHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(bitmapPath, options);
        options.inSampleSize = calculateInSampleSize(options, targetWidth, targetHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(bitmapPath, options);
    }

    /**
     * 高效decode图片
     *
     * @param fileDescriptor {@link FileDescriptor}
     * @param targetWidth    目标宽
     * @param targetHeight   目标高
     * @return Bitmap
     */
    public static Bitmap decodeBitmap(@NonNull FileDescriptor fileDescriptor, int targetWidth, int targetHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
        options.inSampleSize = calculateInSampleSize(options, targetWidth, targetHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
    }

    /**
     * 高效decode图片
     *
     * @param res          {@link Resources}
     * @param resId        资源id
     * @param targetWidth  目标宽
     * @param targetHeight 目标高
     * @return Bitmap
     */
    public static Bitmap decodeBitmap(@NonNull Resources res, @DrawableRes int resId, int targetWidth, int targetHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = calculateInSampleSize(options, targetWidth, targetHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 高效decode图片
     *
     * @param data
     * @param offSet
     * @param length
     * @param targetWidth  目标宽
     * @param targetHeight 目标高
     * @return Bitmap
     */
    public static Bitmap decodeBitmap(@NonNull byte[] data, int offSet, int length, int targetWidth, int targetHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, offSet, length, options);
        options.inSampleSize = calculateInSampleSize(options, targetWidth, targetHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(data, offSet, length, options);
    }

    /**
     * 高效decode图片
     *
     * @param inputStream
     * @param targetWidth  目标宽
     * @param targetHeight 目标高
     * @return Bitmap
     */
    public static Bitmap decodeBitmap(@NonNull InputStream inputStream, int targetWidth, int targetHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);
        options.inSampleSize = calculateInSampleSize(options, targetWidth, targetHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(inputStream, null, options);
    }

    /**
     * 根据目标宽高计算采样率
     *
     * @param options
     * @param targetWidth  目标宽
     * @param targetHeight 目标高
     * @return 采样率
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int targetWidth, int targetHeight) {
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        if (targetWidth > outWidth && targetHeight > outHeight) {
            return 1;
        } else {
            int inSampleSize = 2;
            while ((outWidth / inSampleSize > targetWidth) && (outHeight / inSampleSize > targetHeight)) {
                inSampleSize *= 2;
            }
            return inSampleSize;
        }
    }

    /**
     * 压缩图片文件到指定大小
     *
     * @param filePath    图片文件路径
     * @param maxFileSize 压缩后的最大值  单位：KB
     */
    public static String compressBmpToFile(String filePath, int maxFileSize) {
        return compressBmpToFile(filePath, filePath, maxFileSize);
    }

    /**
     * 压缩图片文件到指定大小
     *
     * @param srcFilePath    源图片文件路径
     * @param targetFilePath 目标图片文件路径
     * @param maxFileSize    压缩后的最大值  单位：KB
     */
    public static String compressBmpToFile(String srcFilePath, String targetFilePath, int maxFileSize) {
        Bitmap bitmap = BitmapFactory.decodeFile(srcFilePath);
        if (null == bitmap)
            return "";

        if (calculateBitmapSize(bitmap) < maxFileSize)
            return srcFilePath;

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int quality = 90;
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        while (baos.toByteArray().length / 1024 > maxFileSize) {
            baos.reset();
            quality -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        }
        try {
            if (StringUtils.isEmpty(targetFilePath)) targetFilePath = srcFilePath;

            final FileOutputStream fos = new FileOutputStream(new File(targetFilePath));
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
            return targetFilePath;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 计算bitmap的大小
     *
     * @param bitmap
     * @return bitmap的大小单位KB
     */
    public static float calculateBitmapSize(Bitmap bitmap) {
        if (null == bitmap) {
            return 0.0f;
        }
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        final float size = (baos.toByteArray().length / 1024);
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }

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
     * 高斯模糊图片
     *
     * @param context 上下文
     * @param bitmap  需要模糊的图片
     * @param radius  模糊半径，值越大，效果越明显
     * @return
     */
    public static Bitmap blurBitmap(Context context, Bitmap bitmap, @IntRange(from = 1, to = 25) int radius) {
        if (null == bitmap) return null;

        RenderScript rs = RenderScript.create(context);
        Allocation allocation = Allocation.createFromBitmap(rs, bitmap);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, allocation.getElement());
        blur.setInput(allocation);
        blur.setRadius(radius);
        blur.forEach(allocation);

        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        allocation.copyTo(outBitmap);

        bitmap.recycle();
        rs.destroy();

        return outBitmap;
    }

    /**
     * 灰度图片
     *
     * @param bitmap 需要模糊的图片
     * @return
     */
    public static Bitmap greyBitmap(Bitmap bitmap) {
        if (null == bitmap) return null;

        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(outBitmap);
        canvas.drawBitmap(bitmap, 0, 0, paint);

        bitmap.recycle();
        return outBitmap;
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

    /**
     * 将图片变为 Base64 数据
     *
     * @param bitmap  需要编码的 {@link Bitmap} 对象
     * @param quality 图片质量(是否压缩图片) 0-100 100表示不压缩
     */
    public static String encodeToString(Bitmap bitmap, int quality) {
        if (CheckUtils.isNull(bitmap)) return "";

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 读取图片到ByteArrayOutputStream
        bitmap.compress(Bitmap.CompressFormat.PNG, quality, baos); //参数如果为100那么就不压缩
        byte[] bytes = baos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    /**
     * 图片 Base64 数据变为 {@link Bitmap}
     *
     * @param baseString Base64 数据
     * @param cleanFlag  是否需要清除 “data:image/*;base64, ”  标识  true：需要 false：不需要
     * @return 解码后的 {@link Bitmap} 对象
     */
    public static Bitmap decodeToBitmap(String baseString, boolean cleanFlag) {
        if (StringUtils.isEmpty(baseString)) return null;

        Bitmap bitmap = null;
        try {
            // 注意:编码后的图片会有“data:image/*;base64, ”标识，在进行解码时我们需要去掉这一部分，否则会导致解码失败
            if (cleanFlag) {
                byte[] bitmapArray = Base64.decode(baseString.split(",")[1], Base64.DEFAULT);
                bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
            } else {
                byte[] bitmapArray = Base64.decode(baseString, Base64.DEFAULT);
                bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 将图片文件转换成Base64编码的字符串
     */
    public static String imageToBase64(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try {
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.NO_CLOSE);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(is);
        }
        return result;
    }


    /**
     * 将Base64编码转换为图片文件
     *
     * @param base64Str
     * @param path
     * @return true
     */
    public static boolean base64ToFile(String base64Str, String path) {
        byte[] data = Base64.decode(base64Str, Base64.NO_WRAP);
        for (int i = 0; i < data.length; i++) {
            if (data[i] < 0) {
                //调整异常数据
                data[i] += 256;
            }
        }
        OutputStream os = null;
        try {
            os = new FileOutputStream(path);
            os.write(data);
            os.flush();
            os.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            IOUtils.close(os);
        }
    }

}
