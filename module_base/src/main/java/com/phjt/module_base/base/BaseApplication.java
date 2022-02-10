package com.phjt.module_base.base;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import com.phjt.module_base.base.delegate.AppDelegate;
import com.phjt.module_base.base.delegate.AppLifecycles;
import com.phjt.module_base.utils.AppContextUtil;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;

/**
 * @author: gengyan
 * date:    2021/9/3 16:38
 * company: GY
 * description: 描述
 */
public class BaseApplication extends Application implements ViewModelStoreOwner {

    private AppLifecycles mAppDelegate;
    private ViewModelStore mAppViewModelStore;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        Timber.plant(new Timber.DebugTree());
        AppContextUtil.init(this);

        if (mAppDelegate == null) {
            mAppDelegate = new AppDelegate(base);
        }
        mAppDelegate.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mAppDelegate != null) {
            mAppDelegate.onCreate(this);
        }

        mAppViewModelStore = new ViewModelStore();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mAppDelegate != null) {
            mAppDelegate.onTerminate(this);
        }
    }

    @NonNull
    @NotNull
    @Override
    public ViewModelStore getViewModelStore() {
        return mAppViewModelStore;
    }
}
