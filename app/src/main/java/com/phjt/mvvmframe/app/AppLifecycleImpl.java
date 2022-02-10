package com.phjt.mvvmframe.app;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.phjt.module_base.base.delegate.AppLifecycles;

import org.jetbrains.annotations.NotNull;

/**
 * @author: gengyan
 * date:    2021/9/3 16:20
 * company: GY
 * description: {@link Application} 的生命周期代理类
 * 代理类是通过在{@link GlobalConfiguration}添加的框架中
 * 在使用时可以根据业务需求创建多个，尤其是在组件化应用中
 * 每个 module 中可以配置一个
 */
public class AppLifecycleImpl implements AppLifecycles {


    @Override
    public void attachBaseContext(@NonNull @NotNull Context base) {

    }

    @Override
    public void onCreate(@NonNull @NotNull Application application) {

    }

    @Override
    public void onTerminate(@NonNull @NotNull Application application) {

    }
}
