package com.phjt.module_base.utils;

import android.content.Context;

/**
 * @author: gengyan
 * date:    2021/9/18 10:19
 * company: GY
 * description: 描述
 */
public class AppContextUtil {

    private static Context mContext;

    /**
     * 基类中初始化上下文
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        mContext = context;
    }

    /**
     * 初始化工具类中上下文对象
     *
     * @return 上下文
     */
    public static Context getInstance() {
        if (mContext == null) {
            throw new NullPointerException("the context is null,please init AppContextUtil in Application first.");
        }
        return mContext;
    }
}
