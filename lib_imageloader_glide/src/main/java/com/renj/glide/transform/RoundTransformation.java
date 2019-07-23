package com.renj.glide.transform;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.renj.imageloaderlibrary.utils.BitmapUtils;

import java.security.MessageDigest;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-01-16   16:36
 * <p>
 * 描述：Glide 中用于将 {@link Bitmap} 变为圆角图片的 {@link BitmapTransformation}
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class RoundTransformation extends BitmapTransformation {
    private int radiusX, radiusY;

    /**
     * 构造
     *
     * @see #RoundTransformation(int)
     * @see #RoundTransformation(int, int)
     */
    public RoundTransformation() {
        this(4, 4);
    }

    /**
     * 构造，指定圆角大小，默认 4
     *
     * @param radius 圆角大小
     * @see #RoundTransformation()
     * @see #RoundTransformation(int, int)
     */
    public RoundTransformation(int radius) {
        this(radius, radius);
    }

    /**
     * 构造，指定x轴方向和y轴方向的圆角大小，默认 4
     *
     * @param radiusX x方向圆角大小
     * @param radiusY y方向圆角大小
     * @see #RoundTransformation()
     * @see #RoundTransformation(int)
     */
    public RoundTransformation(int radiusX, int radiusY) {
        this.radiusX = radiusX;
        this.radiusY = radiusY;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        if (toTransform == null) return null;
        return BitmapUtils.makeRoundRect(toTransform, radiusX, radiusY);
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }
}
