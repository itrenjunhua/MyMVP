package com.renj.utils.res;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import java.io.*;

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
    public static void compressBmpToFile(String filePath, int maxFileSize) {
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        if (null == bitmap)
            return;

        if (calculateBitmapSize(bitmap) < maxFileSize)
            return;

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int quality = 90;
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        while (baos.toByteArray().length / 1024 > maxFileSize) {
            baos.reset();
            quality -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        }
        try {
            final FileOutputStream fos = new FileOutputStream(new File(filePath));
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
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
}
