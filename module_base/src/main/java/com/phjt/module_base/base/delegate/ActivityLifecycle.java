package com.phjt.module_base.base.delegate;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.phjt.module_base.config.AppManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author: gengyan
 * date:    2021/9/3 17:48
 * company: GY
 * description: 框架内部管理的  ActivityLifecycle
 */
public class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {

    private static final String TAG = "ActivityLifecycle";

    private static class Holder {
        private static final ActivityLifecycle INSTANCE = new ActivityLifecycle();
    }

    public static ActivityLifecycle getInstance() {
        return Holder.INSTANCE;
    }

    private ActivityLifecycle() {
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
        //如果 intent 包含了此字段,并且为 true 说明不加入到 list 进行统一管理
        boolean isNotAdd = false;

        if (activity.getIntent() != null) {
            isNotAdd = activity.getIntent().getBooleanExtra(AppManager.IS_NOT_ADD_ACTIVITY_LIST, false);
        }

        if (!isNotAdd) {
            AppManager.getAppManager().addActivity(activity);
        }
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        AppManager.getAppManager().setCurrentActivity(activity);
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        if (AppManager.getAppManager().getCurrentActivity() == activity) {
            AppManager.getAppManager().setCurrentActivity(null);
        }
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        AppManager.getAppManager().removeActivity(activity);
    }
}
