package com.phjt.module_base.config;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.phjt.module_base.base.delegate.AppLifecycles;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * @author: gengyan
 * date:    2021/9/3 16:22
 * company: GY
 * description: 描述
 */
public interface ConfigModule {

    /**
     * 配置各模块在以 app 全量编译时，模块的初始化顺序
     *
     * @return
     */
    int configModulePriority();

    /**
     * 使用 {@link GlobalConfigModule.Builder} 给框架配置一些配置参数
     *
     * @param context {@link Context}
     * @param builder {@link GlobalConfigModule.Builder}
     */
    void applyOptions(@NonNull Context context, @NonNull GlobalConfigModule.Builder builder);

    /**
     * 使用 {@link AppLifecycles} 在 {@link Application} 的生命周期中注入一些操作
     *
     * @param context    {@link Context}
     * @param lifecycles {@link Application} 的生命周期容器, 可向框架中添加多个 {@link Application} 的生命周期类
     */
    void injectAppLifecycle(@NonNull Context context, @NonNull List<AppLifecycles> lifecycles);

    /**
     * 使用 {@link Application.ActivityLifecycleCallbacks} 在 {@link Activity} 的生命周期中注入一些操作
     *
     * @param context    {@link Context}
     * @param lifecycles {@link Activity} 的生命周期容器, 可向框架中添加多个 {@link Activity} 的生命周期类
     */
    void injectActivityLifecycle(@NonNull Context context,
                                 @NonNull List<Application.ActivityLifecycleCallbacks> lifecycles);
}
