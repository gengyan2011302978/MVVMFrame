package com.phjt.module_base.imageloader.strategy;

import android.widget.ImageView;

/**
 * @author: gengyan
 * date:    2021/9/17
 * company: GY
 * description: 基础配置
 */
public class ImageConfig {

    protected Object loadRes;
    protected ImageView imageView;
    protected int placeholder;//占位符
    protected int errorPic;//错误占位符

    public Object getLoadRes() {
        return loadRes;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getPlaceholder() {
        return placeholder;
    }

    public int getErrorPic() {
        return errorPic;
    }
}
