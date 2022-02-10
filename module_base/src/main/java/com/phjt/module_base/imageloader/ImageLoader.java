package com.phjt.module_base.imageloader;

import android.content.Context;

import androidx.annotation.Nullable;

import com.phjt.module_base.base.delegate.AppDelegate;
import com.phjt.module_base.config.ConfigModule;
import com.phjt.module_base.config.GlobalConfigModule;
import com.phjt.module_base.imageloader.strategy.BaseImageLoaderStrategy;
import com.phjt.module_base.imageloader.strategy.ImageConfig;
import com.phjt.module_base.utils.Preconditions;

/**
 * @author: gengyan
 * date:    2021/9/17 15:25
 * company: GY
 * description: 图片加载入口配置
 * <p>
 * {@link GlobalConfigModule}默认图片加载策略为GlideImageLoaderStrategy
 * 可通过{@link ConfigModule}在{@link GlobalConfigModule}中进行初始化配置 或 {ImageLoader.setLoadImgStrategy()}进行替换
 */
public class ImageLoader {

    private static class Holder {
        private static final ImageLoader INSTANCE = new ImageLoader();
    }

    public static ImageLoader getInstance() {
        return Holder.INSTANCE;
    }

    private ImageLoader() {

    }

    /**
     * 图片加载的策略 在{@link AppDelegate}中进行初始化配置
     */
    private BaseImageLoaderStrategy mStrategy;


    /**
     * 加载图片
     */
    public <T extends ImageConfig> void loadImage(Context context, T config) {
        Preconditions.checkNotNull(mStrategy,
                "Please implement BaseImageLoaderStrategy and call GlobalConfigModule.Builder#imageLoaderStrategy(BaseImageLoaderStrategy) in the applyOptions method of ConfigModule");
        this.mStrategy.loadImage(context, config);
    }

    /**
     * 停止加载或清理缓存
     *
     * @param context
     * @param config
     * @param <T>
     */
    public <T extends ImageConfig> void clear(Context context, T config) {
        Preconditions.checkNotNull(mStrategy, "Please implement BaseImageLoaderStrategy and call GlobalConfigModule.Builder#imageLoaderStrategy(BaseImageLoaderStrategy) in the applyOptions method of ConfigModule");
        this.mStrategy.clear(context, config);
    }

    /**
     * 可在运行时随意切换 {@link BaseImageLoaderStrategy}
     *
     * @param strategy
     */
    public void setLoadImgStrategy(BaseImageLoaderStrategy strategy) {
        Preconditions.checkNotNull(strategy, "strategy == null");
        this.mStrategy = strategy;
    }


    @Nullable
    public BaseImageLoaderStrategy getLoadImgStrategy() {
        return mStrategy;
    }

}
