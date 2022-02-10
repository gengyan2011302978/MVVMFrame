package com.phjt.mvvmframe.app;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.phjt.module_base.base.delegate.AppLifecycles;
import com.phjt.module_base.config.ConfigModule;
import com.phjt.module_base.config.GlobalConfigModule;
import com.phjt.module_base.http.log.RequestInterceptor;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author: gengyan
 * date:    2021/9/3 16:27
 * company: GY
 * description: 统一配置类 此类在 Manifest 文件中配置, 通过反射将框架内部会进行配置分配
 */
public class GlobalConfiguration implements ConfigModule {

    @Override
    public int configModulePriority() {
        return 0;
    }

    @Override
    public void applyOptions(@NonNull @NotNull Context context, @NonNull @NotNull GlobalConfigModule.Builder builder) {
        builder.printHttpLogLevel(RequestInterceptor.Level.ALL)
                .baseurl("https://test-shangwuyou-api.peogoo.com/")
                .globalHttpHandler(new GlobalHttpHandlerImpl());
    }

    @Override
    public void injectAppLifecycle(@NonNull @NotNull Context context, @NonNull @NotNull List<AppLifecycles> lifecycles) {
        lifecycles.add(new AppLifecycleImpl());
    }

    @Override
    public void injectActivityLifecycle(@NonNull @NotNull Context context, @NonNull @NotNull List<Application.ActivityLifecycleCallbacks> lifecycles) {
        lifecycles.add(new ActivityLifecycleImpl());
    }
}
