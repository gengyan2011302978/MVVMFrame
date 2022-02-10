package com.phjt.module_base.imageloader;

import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;

import com.phjt.module_base.imageloader.strategy.ImageConfig;

/**
 * @author: gengyan
 * date:    2021/9/17
 * company: GY
 * description: 扩展配置
 */
public class ImageConfigImpl extends ImageConfig {
    /**
     * 缓存策略
     */
    private int cacheStrategy;
    /**
     * 图片圆角
     */
    private int imageRadius;
    /**
     * 高斯模糊
     */
    private int blurValue;

    /**
     *
     */
    private ImageView[] imageViews;

    /**
     * 是否使用淡入淡出过渡动画
     */
    private boolean isCrossFade;

    /**
     * 是否将图片剪切为 CenterCrop
     */
    private boolean isCenterCrop;

    /**
     * 是否将图片剪切为圆形
     */
    private boolean isCircle;

    /**
     * 清理内存缓存
     */
    private boolean isClearMemory;
    /**
     * 清理本地缓存
     */
    private boolean isClearDiskCache;

    public ImageConfigImpl(Builder builder) {
        this.loadRes = builder.loadRes;
        this.imageView = builder.imageView;
        this.placeholder = builder.placeholder;
        this.errorPic = builder.errorPic;
        this.imageRadius = builder.imageRadius;
        this.blurValue = builder.blurValue;
        this.imageViews = builder.imageViews;
        this.cacheStrategy = builder.cacheStrategy;
        this.isCircle = builder.isCircle;
        this.isCrossFade = builder.isCrossFade;
        this.isCenterCrop = builder.isCenterCrop;
        this.isClearMemory = builder.isClearMemory;
        this.isClearDiskCache = builder.isClearDiskCache;

    }

    public int getCacheStrategy() {
        return cacheStrategy;
    }

    public boolean isCrossFade() {
        return isCrossFade;
    }

    public boolean isCenterCrop() {
        return isCenterCrop;
    }

    public boolean isCircle() {
        return isCircle;
    }

    public boolean isImageRadius() {
        return imageRadius > 0;
    }

    public int getImageRadius() {
        return imageRadius;
    }

    public boolean isBlurImage() {
        return blurValue > 0;
    }

    public int getBlurValue() {
        return blurValue;
    }

    public ImageView[] getImageViews() {
        return imageViews;
    }

    public boolean isClearMemory() {
        return isClearMemory;
    }

    public boolean isClearDiskCache() {
        return isClearDiskCache;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Object loadRes;
        private ImageView imageView;
        private int placeholder;
        private int errorPic;
        private int imageRadius;

        private int blurValue;

        private ImageView[] imageViews;
        private int cacheStrategy;
        private boolean isCircle;
        private boolean isCrossFade;
        private boolean isCenterCrop;

        private boolean isClearMemory;//清理内存缓存
        private boolean isClearDiskCache;//清理本地缓存

        /**
         * 设置 图片 路径
         */
        public Builder url(String url) {
            this.loadRes = url;
            return this;
        }

        public Builder res(@RawRes @DrawableRes @Nullable Integer id) {
            this.loadRes = id;
            return this;
        }

        /**
         * 设置图片加载占位符
         */
        public Builder placeHolder(int placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        /**
         * 加载失败
         *
         * @param errorPic errorPic
         * @return Builder
         */
        public Builder errorPic(int errorPic) {
            this.errorPic = errorPic;
            return this;
        }

        /**
         * 设置 高斯模糊
         *
         * @param blurValue
         * @return
         */
        public Builder blurValue(int blurValue) {
            this.blurValue = blurValue;
            return this;
        }

        /**
         * 清除缓存 指定 ImageView
         *
         * @param views 要清除缓存的 ImageView
         * @return
         */
        public Builder setImageViews(ImageView... views) {
            this.imageViews = views;
            return this;
        }

        /**
         * 设置图片圆角
         *
         * @param imageRadius
         * @return
         */
        public Builder imageRadius(int imageRadius) {
            this.imageRadius = imageRadius;
            return this;
        }

        /**
         * 设置图片是否使用渐变加载
         *
         * @param crossFade
         * @return
         */
        public Builder crossFade(boolean crossFade) {
            this.isCrossFade = crossFade;
            return this;
        }


        public Builder centerCrop(boolean centerCrop) {
            this.isCenterCrop = centerCrop;
            return this;
        }

        /**
         * 设置图片是否是圆形
         *
         * @param circle
         * @return
         */
        public Builder circle(boolean circle) {
            this.isCircle = circle;
            return this;
        }

        /**
         * 设置目标 ImageView
         *
         * @param imageView 目标 ImageView
         * @return Builder
         */
        public Builder imageView(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        /**
         * 清除内存缓存
         *
         * @param clearMemory
         */
        public void clearMemory(boolean clearMemory) {
            isClearMemory = clearMemory;
        }

        /**
         * 清除本地缓存
         *
         * @param clearDiskCache
         */
        public void clearDiskCache(boolean clearDiskCache) {
            isClearDiskCache = clearDiskCache;
        }

        /**
         * 设置Glide图片缓存策略
         */
        public Builder setCacheStrategy(int cacheStrategy) {
            this.cacheStrategy = cacheStrategy;
            return this;
        }

        public ImageConfig build() {
            return new ImageConfigImpl(this);
        }
    }
}
