package com.phjt.module_base.imageloader.strategy;

import android.content.Context;

import androidx.annotation.Nullable;

/**
 * @author: gengyan
 * date:    2021/9/17
 * company: GY
 * description: 图片加载策略
 */
public interface BaseImageLoaderStrategy<T extends ImageConfig> {

    /**
     * 加载图片
     *
     * @param context {@link Context}
     * @param config  图片加载配置信息
     */
    void loadImage(@Nullable Context context, @Nullable T config);

    /**
     * 停止加载
     *
     * @param context {@link Context}
     * @param config  图片加载配置信息
     */
    void clear(@Nullable Context context, @Nullable T config);
}
