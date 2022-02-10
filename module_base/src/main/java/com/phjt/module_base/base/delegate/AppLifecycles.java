package com.phjt.module_base.base.delegate;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

/**
 * @author: gengyan
 * date:    2021/9/3 16:17
 * company: GY
 * description: 用于代理{@link Application}的生命周期
 */
public interface AppLifecycles {

    void attachBaseContext(@NonNull Context base);

    void onCreate(@NonNull Application application);

    void onTerminate(@NonNull Application application);

}
