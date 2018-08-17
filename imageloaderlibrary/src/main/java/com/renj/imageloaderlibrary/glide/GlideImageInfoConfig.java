package com.renj.imageloaderlibrary.glide;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;

import com.renj.imageloaderlibrary.loader.ImageInfoConfig;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2018-05-27   17:33
 * <p>
 * 描述：Glide框架加载图片信息配置类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class GlideImageInfoConfig extends ImageInfoConfig {
    private byte[] bytes; // 图片字节数组
    private Bitmap bitmap; // Bitmap对象
    private Drawable drawable; // Drawable 对象

    private boolean isGif; // 是否 Gif 图片
    private boolean isBitmap; // 是否作为 Bitmap 显示

    @FloatRange(from = 0)
    private float thumbnail; // 缩略图缩放倍数


    private GlideImageInfoConfig(Builder builder) {
        super(builder);
        this.bytes = builder.bytes;
        this.bitmap = builder.bitmap;
        this.drawable = builder.drawable;
        this.isGif = builder.isGif;
        this.isBitmap = builder.isBitmap;
        this.thumbnail = builder.thumbnail;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public boolean isGif() {
        return isGif;
    }

    public boolean isBitmap() {
        return isBitmap;
    }

    public float getThumbnail() {
        return thumbnail;
    }


    public static class Builder extends ImageInfoConfig.Builder {
        private byte[] bytes; // 图片字节数组
        private Bitmap bitmap; // Bitmap对象
        private Drawable drawable; // Drawable 对象

        private boolean isGif; // 是否 Gif 图片
        private boolean isBitmap; // 是否作为 Bitmap 显示

        @FloatRange(from = 0)
        private float thumbnail; // 缩略图缩放倍数

        /**
         * 指定图片的字节数组数据
         *
         * @param bytes 图片的字节数组数据
         */
        public Builder bytes(@NonNull byte[] bytes) {
            this.bytes = bytes;
            return this;
        }

        /**
         * 指定 {@link Bitmap} 对象
         *
         * @param bitmap {@link Bitmap} 对象
         */
        public Builder bitmap(@NonNull Bitmap bitmap) {
            this.bitmap = bitmap;
            return this;
        }

        /**
         * {@link Drawable} 对象
         *
         * @param drawable {@link Drawable} 对象
         */
        public Builder drawable(@NonNull Drawable drawable) {
            this.drawable = drawable;
            return this;
        }

        /**
         * 图片作为 gif 图片
         */
        public Builder asGif() {
            this.isGif = true;
            return this;
        }

        /**
         * 将图片转换为 {@link Bitmap}
         */
        public Builder asBitmap() {
            this.isBitmap = true;
            return this;
        }

        /**
         * 指定 缩略图缩放倍数
         *
         * @param thumbnail 缩略图缩放倍数
         */
        public Builder thumbnail(@FloatRange(from = 0) float thumbnail) {
            this.thumbnail = thumbnail;
            return this;
        }

        /**
         * 构建 {@link ImageInfoConfig} 对象
         *
         * @return {@link ImageInfoConfig} 对象
         */
        public GlideImageInfoConfig build() {
            return new GlideImageInfoConfig(this);
        }
    }
}
