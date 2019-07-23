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
 * 创建时间：2019-01-15   17:09
 * <p>
 * 描述：Glide框架 旋转 Transformation
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class RotateTransformation extends BitmapTransformation {
    private float rotateRotationAngle = 0f;
    private float pivotX = 0f, pivotY = 0f;

    public RotateTransformation(float rotateRotationAngle) {
        this.rotateRotationAngle = rotateRotationAngle;
    }

    public RotateTransformation(float rotateRotationAngle, float pivotX, float pivotY) {
        this.rotateRotationAngle = rotateRotationAngle;
        this.pivotX = pivotX;
        this.pivotY = pivotY;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        if (toTransform == null) return null;
        return BitmapUtils.rotate(toTransform, rotateRotationAngle, pivotX, pivotY);
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
    }
}
