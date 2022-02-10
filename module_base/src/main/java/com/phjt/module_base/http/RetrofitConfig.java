package com.phjt.module_base.http;

import android.app.Application;
import android.content.Context;

import com.phjt.module_base.config.GlobalConfigModule;
import com.phjt.module_base.http.log.RequestInterceptor;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author: gengyan
 * date:    2021/9/8 14:08
 * company: GY
 * description: Retrofit/okhttp 公共配置、初始化
 */
public class RetrofitConfig {

    private static final int TIME_OUT = 30;

    private Retrofit mRetrofit;

    private static class Holder {
        private static final RetrofitConfig INSTANCE = new RetrofitConfig();
    }

    public static RetrofitConfig getInstance() {
        return Holder.INSTANCE;
    }

    private RetrofitConfig() {

    }

    /**
     * 提供 {@link Retrofit}
     */
    public Retrofit getRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder().build();
        }
        return mRetrofit;
    }

    /**
     * 赋值 {@link Retrofit}
     *
     * @param application  {@link Application}
     * @param configModule {@link GlobalConfigModule}
     */
    public void initRetrofit(Application application, GlobalConfigModule configModule) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(configModule.getBaseUrl())
                .client(getOkHttpClient(application, configModule));

        if (configModule.getRetrofitConfiguration() != null) {
            configModule.getRetrofitConfiguration().configRetrofit(application, builder);
        }

        builder.addConverterFactory(GsonConverterFactory.create());

        mRetrofit = builder.build();
    }


    /**
     * 提供 {@link OkHttpClient}
     *
     * @param application  {@link Application}
     * @param configModule {@link GlobalConfigModule}
     * @return {@link OkHttpClient}
     */
    public OkHttpClient getOkHttpClient(Application application, GlobalConfigModule configModule) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(new RequestInterceptor(configModule.getHandler(),
                        configModule.getFormatPrinter(), configModule.getPrintHttpLogLevel()));

        if (configModule.getHandler() != null) {
            builder.addInterceptor(chain ->
                    chain.proceed(configModule.getHandler().onHttpRequestBefore(chain, chain.request())));
        }

        //如果外部提供了 Interceptor 的集合则遍历添加
        if (configModule.getInterceptors() != null) {
            for (Interceptor interceptor : configModule.getInterceptors()) {
                builder.addInterceptor(interceptor);
            }
        }

        //为 OkHttp 设置默认的线程池
        builder.dispatcher(new Dispatcher(configModule.getExecutorService()));

        //加载外部扩展的OkHttp配置
        if (configModule.getOkhttpConfiguration() != null) {
            configModule.getOkhttpConfiguration().configOkhttp(application, builder);
        }

        return builder.build();
    }

    /**
     * {@link Retrofit} 自定义配置接口
     */
    public interface RetrofitConfiguration {
        void configRetrofit(@NonNull Context context, @NonNull Retrofit.Builder builder);
    }


    /**
     * {@link OkHttpClient} 自定义配置接口
     */
    public interface OkhttpConfiguration {
        void configOkhttp(@NonNull Context context, @NonNull OkHttpClient.Builder builder);
    }
}
