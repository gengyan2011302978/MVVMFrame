package com.phjt.mvvmframe.common

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.Nullable
import com.phjt.module_base.imageloader.ImageConfigImpl
import com.phjt.module_base.imageloader.ImageLoader

/**
 * @author: gengyan
 * date:    2021/9/17 15:45
 * company: GY
 * description: 业务层封装图片加载
 */
class AppImageLoader private constructor() {

    companion object {

        /**
         * 加载本地图片
         */
        fun loadRes(@Nullable resId: Int, @Nullable imageView: ImageView) {
            ImageLoader.getInstance().loadImgStrategy?.loadImage(
                imageView.context,
                ImageConfigImpl.builder()
                    .res(resId).imageView(imageView)
                    .build()
            )
        }

        /**
         * 加载网络图片
         */
        fun loadUrl(@Nullable url: String, @Nullable imageView: ImageView) {
            ImageLoader.getInstance().loadImgStrategy?.loadImage(
                imageView.context,
                ImageConfigImpl.builder()
                    .url(url).imageView(imageView)
                    .build()
            )
        }

        /**
         * 加载网络图片 带holderImage
         */
        fun loadUrlHolder(
            @Nullable url: String,
            @Nullable imageView: ImageView,
            @DrawableRes holderImage: Int
        ) {
            ImageLoader.getInstance().loadImgStrategy?.loadImage(
                imageView.context,
                ImageConfigImpl.builder()
                    .url(url).placeHolder(holderImage).imageView(imageView)
                    .build()
            )
        }

        /**
         * 加载网络图片 带errorImage
         */
        fun loadUrlError(
            @Nullable url: String,
            @Nullable imageView: ImageView,
            @DrawableRes errorImage: Int
        ) {
            ImageLoader.getInstance().loadImgStrategy?.loadImage(
                imageView.context,
                ImageConfigImpl.builder()
                    .url(url).errorPic(errorImage).imageView(imageView)
                    .build()
            )
        }

        /**
         * 加载网络图片 带holderImage 和 errorImage
         */
        fun loadUrl(
            @Nullable url: String,
            @Nullable imageView: ImageView,
            @DrawableRes holderImage: Int,
            @DrawableRes errorImage: Int
        ) {
            ImageLoader.getInstance().loadImgStrategy?.loadImage(
                imageView.context,
                ImageConfigImpl.builder().url(url).placeHolder(holderImage).errorPic(errorImage)
                    .imageView(imageView)
                    .build()
            )
        }

        /**
         * 加载圆形图片
         */
        fun loadRoundImg(
            @Nullable url: String,
            @Nullable imageView: ImageView,
            @DrawableRes errorImage: Int
        ) {
            ImageLoader.getInstance().loadImgStrategy?.loadImage(
                imageView.context,
                ImageConfigImpl.builder()
                    .url(url).circle(true).errorPic(errorImage).imageView(imageView)
                    .build()
            )
        }
    }

}