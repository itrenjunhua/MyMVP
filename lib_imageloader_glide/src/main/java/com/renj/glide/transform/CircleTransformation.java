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
 * 创建时间：2019-01-16   16:54
 * <p>
 * 描述：Glide 中用于将 {@link Bitmap} 变为圆形图片的 {@link BitmapTransformation}
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class CircleTransformation extends BitmapTransformation {
    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        if (toTransform == null) return null;
        return BitmapUtils.makeCircleImage(toTransform);
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }
}
