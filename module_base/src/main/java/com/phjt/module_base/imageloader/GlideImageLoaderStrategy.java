package com.phjt.module_base.imageloader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.phjt.module_base.imageloader.strategy.BaseImageLoaderStrategy;
import com.phjt.module_base.utils.Preconditions;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: gengyan
 * date:    2021/9/17 14:17
 * company: GY
 * description: Glide图片加载策略
 */
public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy<ImageConfigImpl> {

    @Override
    public void loadImage(@Nullable Context context, @Nullable ImageConfigImpl config) {
        Preconditions.checkNotNull(context, "Context is required");
        Preconditions.checkNotNull(config, "ImageConfigImpl is required");
        Preconditions.checkNotNull(config.getImageView(), "ImageView is required");

        RequestBuilder<Drawable> requestBuilder = Glide.with(context).load(config.getLoadRes());

        switch (config.getCacheStrategy()) {
            case 0:
                requestBuilder.diskCacheStrategy(DiskCacheStrategy.ALL);
                break;
            case 1:
                requestBuilder.diskCacheStrategy(DiskCacheStrategy.NONE);
                break;
            case 2:
                requestBuilder.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                break;
            case 3:
                requestBuilder.diskCacheStrategy(DiskCacheStrategy.DATA);
                break;
            case 4:
                requestBuilder.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
                break;
            default:
                requestBuilder.diskCacheStrategy(DiskCacheStrategy.ALL);
                break;
        }

        if (config.isCrossFade()) {
            requestBuilder.transition(DrawableTransitionOptions.withCrossFade());
        }

        if (config.isCenterCrop()) {
            requestBuilder.centerCrop();
        }

        if (config.isCircle()) {
            requestBuilder.circleCrop();
        }

        if (config.isImageRadius()) {
            requestBuilder.transform(new RoundedCorners(config.getImageRadius()));
        }

        if (config.isBlurImage() && config.isCenterCrop()) {
            requestBuilder.transform(new BlurTransformation(config.getBlurValue()), new CenterCrop());
        }

        if (config.isBlurImage()) {
            requestBuilder.transform(new BlurTransformation(config.getBlurValue()));
        }

        if (config.getPlaceholder() != 0) {
            requestBuilder.placeholder(config.getPlaceholder());
        }

        if (config.getErrorPic() != 0) {
            requestBuilder.error(config.getErrorPic());
        }

        requestBuilder.into(config.getImageView());
    }

    @Override
    public void clear(@Nullable Context context, @Nullable ImageConfigImpl config) {
        Preconditions.checkNotNull(context, "Context is required");
        Preconditions.checkNotNull(config, "ImageConfigImpl is required");

        if (config.getImageView() != null) {
            Glide.get(context).getRequestManagerRetriever().get(context).clear(config.getImageView());
        }

        //取消在执行的任务并且释放资源
        if (config.getImageViews() != null && config.getImageViews().length > 0) {
            for (ImageView imageView : config.getImageViews()) {
                Glide.get(context).getRequestManagerRetriever().get(context).clear(imageView);
            }
        }

        //清除内存缓存
        if (config.isClearMemory()) {
            Glide.get(context).clearMemory();
        }

        //清除本地磁盘缓存
        if (config.isClearDiskCache()) {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> Glide.get(context).clearDiskCache());
        }
    }
}
