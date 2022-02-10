package com.phjt.module_base.base.delegate;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.phjt.module_base.config.ConfigModule;
import com.phjt.module_base.config.GlobalConfigModule;
import com.phjt.module_base.config.ManifestParser;
import com.phjt.module_base.http.RetrofitConfig;
import com.phjt.module_base.imageloader.ImageLoader;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


/**
 * @author: gengyan
 * date:    2021/9/3 16:29
 * company: GY
 * description: {@link Application} 的代理类，用户初始化App的配置信息
 */
public class AppDelegate implements AppLifecycles {

    private Application mApplication;
    private Application.ActivityLifecycleCallbacks mActivityLifecycle;

    private final List<ConfigModule> mModules;
    private List<AppLifecycles> mAppLifecycles = new ArrayList<>();
    private List<Application.ActivityLifecycleCallbacks> mActivityLifecycles = new ArrayList<>();

    public AppDelegate(@NonNull Context context) {
        //用反射, 将 AndroidManifest.xml 中带有 ConfigModule 标签的 class 转成对象集合（List<ConfigModule>）
        mModules = new ManifestParser(context).parse();

        for (ConfigModule module : mModules) {
            //将框架外部, 开发者实现的 Application 的生命周期回调 (AppLifecycles) 存入 mAppLifecycles 集合 (此时还未注册回调)
            module.injectAppLifecycle(context, mAppLifecycles);
            //将框架外部, 开发者实现的 Activity 的生命周期回调 (ActivityLifecycleCallbacks) 存入 mActivityLifecycles 集合 (此时还未注册回调)
            module.injectActivityLifecycle(context, mActivityLifecycles);
        }

        mActivityLifecycle = ActivityLifecycle.getInstance();
    }

    @Override
    public void attachBaseContext(@NonNull @NotNull Context base) {
        //遍历 mAppLifecycles, 执行所有已注册的 AppLifecycles 的 attachBaseContext() 方法 (框架外部, 开发者扩展的逻辑)
        for (AppLifecycles lifecycle : mAppLifecycles) {
            lifecycle.attachBaseContext(base);
        }
    }

    @Override
    public void onCreate(@NonNull @NotNull Application application) {
        this.mApplication = application;
        //执行框架外部, 开发者扩展的 App onCreate 逻辑
        for (AppLifecycles lifecycle : mAppLifecycles) {
            lifecycle.onCreate(mApplication);
        }

        //注册框架内部已实现的 Activity 生命周期逻辑
        mApplication.registerActivityLifecycleCallbacks(mActivityLifecycle);

        //注册框架外部, 开发者扩展的 Activity 生命周期逻辑
        //每个 ConfigModule 的实现类可以声明多个 Activity 的生命周期回调
        for (Application.ActivityLifecycleCallbacks lifecycle : mActivityLifecycles) {
            mApplication.registerActivityLifecycleCallbacks(lifecycle);
        }

        loadGlobalConfigModule();
    }

    /**
     * 将app的全局配置信息封装进module
     * 需要在AndroidManifest中声明{@link ConfigModule}的实现类
     */
    private void loadGlobalConfigModule() {
        GlobalConfigModule.Builder builder = GlobalConfigModule.builder();

        for (ConfigModule module : mModules) {
            module.applyOptions(mApplication, builder);
        }

        //Retrofit初始化配置
        RetrofitConfig.getInstance().initRetrofit(mApplication, builder.build());

        //图片加载配置
        ImageLoader.getInstance().setLoadImgStrategy(builder.build().getLoaderStrategy());
    }

    @Override
    public void onTerminate(@NonNull @NotNull Application application) {
        if (mAppLifecycles != null && mAppLifecycles.size() > 0) {
            for (AppLifecycles lifecycle : mAppLifecycles) {
                lifecycle.onTerminate(mApplication);
            }
        }
        if (mActivityLifecycle != null) {
            mApplication.unregisterActivityLifecycleCallbacks(mActivityLifecycle);
        }
        if (mActivityLifecycles != null && mActivityLifecycles.size() > 0) {
            for (Application.ActivityLifecycleCallbacks lifecycle : mActivityLifecycles) {
                mApplication.unregisterActivityLifecycleCallbacks(lifecycle);
            }
        }
        this.mAppLifecycles = null;
        this.mActivityLifecycle = null;
        this.mActivityLifecycles = null;
    }
}
