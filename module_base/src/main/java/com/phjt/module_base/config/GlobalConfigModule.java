package com.phjt.module_base.config;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.phjt.module_base.config.cache.Cache;
import com.phjt.module_base.config.cache.CacheType;
import com.phjt.module_base.config.cache.IntelligentCache;
import com.phjt.module_base.config.cache.LruCache;
import com.phjt.module_base.http.BaseUrl;
import com.phjt.module_base.http.GlobalHttpHandler;
import com.phjt.module_base.http.ResponseErrorListener;
import com.phjt.module_base.http.RetrofitConfig;
import com.phjt.module_base.http.log.DefaultFormatPrinter;
import com.phjt.module_base.http.log.FormatPrinter;
import com.phjt.module_base.http.log.RequestInterceptor;
import com.phjt.module_base.imageloader.GlideImageLoaderStrategy;
import com.phjt.module_base.imageloader.strategy.BaseImageLoaderStrategy;
import com.phjt.module_base.utils.DataHelper;
import com.phjt.module_base.utils.Preconditions;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.internal.Util;

/**
 * @author: gengyan
 * date:    2021/9/3 16:27
 * company: GY
 * description: 公共配置
 */
public class GlobalConfigModule {

    private HttpUrl mApiUrl;
    private BaseUrl mBaseUrl;
    private BaseImageLoaderStrategy mLoaderStrategy;
    private Cache.Factory mCacheFactory;
    private File mCacheFile;
    private ResponseErrorListener mErrorListener;
    private List<Interceptor> mInterceptors;
    private GlobalHttpHandler mHandler;
    private RetrofitConfig.RetrofitConfiguration mRetrofitConfiguration;
    private RetrofitConfig.OkhttpConfiguration mOkhttpConfiguration;
    private RequestInterceptor.Level mPrintHttpLogLevel;
    private FormatPrinter mFormatPrinter;
    private ExecutorService mExecutorService;

    public GlobalConfigModule(Builder builder) {
        this.mApiUrl = builder.apiUrl;
        this.mBaseUrl = builder.baseUrl;
        this.mLoaderStrategy = builder.loaderStrategy;
        this.mHandler = builder.handler;
        this.mOkhttpConfiguration = builder.okhttpConfiguration;
        this.mCacheFactory = builder.cacheFactory;
        this.mErrorListener = builder.responseErrorListener;
        this.mPrintHttpLogLevel = builder.printHttpLogLevel;
    }

    public static Builder builder() {
        return new Builder();
    }

    public HttpUrl getApiUrl() {
        return mApiUrl;
    }

    public HttpUrl getBaseUrl() {
        if (mBaseUrl != null) {
            HttpUrl httpUrl = mBaseUrl.url();
            if (httpUrl != null) {
                return httpUrl;
            }
        }
        return mApiUrl == null ? HttpUrl.parse("https://api.github.com/") : mApiUrl;
    }

    public BaseImageLoaderStrategy getLoaderStrategy() {
        return mLoaderStrategy == null ? new GlideImageLoaderStrategy() : mLoaderStrategy;
    }

    public Cache.Factory getCacheFactory(Application application) {
        return mCacheFactory == null ? new Cache.Factory() {
            @NonNull
            @Override
            public Cache build(CacheType type) {
                //若想自定义 LruCache 的 size, 或者不想使用 LruCache, 想使用自己自定义的策略
                //使用 GlobalConfigModule.Builder#cacheFactory() 即可扩展

                switch (type.getCacheTypeId()) {
                    //Activity、Fragment 以及 Extras 使用 IntelligentCache (具有 LruCache 和 可永久存储数据的 Map)
                    case CacheType.EXTRAS_TYPE_ID:
                    case CacheType.ACTIVITY_CACHE_TYPE_ID:
                    case CacheType.FRAGMENT_CACHE_TYPE_ID:
                        return new IntelligentCache(type.calculateCacheSize(application));
                    //其余使用 LruCache (当达到最大容量时可根据 LRU 算法抛弃不合规数据)
                    default:
                        return new LruCache(type.calculateCacheSize(application));
                }
            }
        } : mCacheFactory;
    }

    public File getCacheFile(Application application) {
        return mCacheFile == null ? DataHelper.getCacheFile(application) : mCacheFile;
    }

    public ResponseErrorListener getErrorListener() {
        return mErrorListener == null ? ResponseErrorListener.EMPTY : mErrorListener;
    }

    public List<Interceptor> getInterceptors() {
        return mInterceptors;
    }

    public GlobalHttpHandler getHandler() {
        return mHandler;
    }

    public RetrofitConfig.RetrofitConfiguration getRetrofitConfiguration() {
        return mRetrofitConfiguration;
    }

    public RetrofitConfig.OkhttpConfiguration getOkhttpConfiguration() {
        return mOkhttpConfiguration;
    }

    public RequestInterceptor.Level getPrintHttpLogLevel() {
        return mPrintHttpLogLevel == null ? RequestInterceptor.Level.ALL : mPrintHttpLogLevel;
    }

    public FormatPrinter getFormatPrinter() {
        return mFormatPrinter == null ? new DefaultFormatPrinter() : mFormatPrinter;
    }

    public ExecutorService getExecutorService() {
        return mExecutorService == null ? new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60,
                TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(), Util.threadFactory("Arms Executor", false))
                : mExecutorService;
    }

    public static final class Builder {
        private HttpUrl apiUrl;
        private BaseUrl baseUrl;
        private BaseImageLoaderStrategy loaderStrategy;
        private Cache.Factory cacheFactory;
        private File cacheFile;
        private ResponseErrorListener responseErrorListener;
        private List<Interceptor> interceptors;
        private GlobalHttpHandler handler;
        private RetrofitConfig.RetrofitConfiguration retrofitConfiguration;
        private RetrofitConfig.OkhttpConfiguration okhttpConfiguration;
        private RequestInterceptor.Level printHttpLogLevel;
        private FormatPrinter formatPrinter;
        private ExecutorService executorService;

        private Builder() {
        }

        /**
         * 设置BaseUrl
         */
        public Builder baseurl(String baseUrl) {//基础url
            if (TextUtils.isEmpty(baseUrl)) {
                throw new NullPointerException("BaseUrl can not be empty");
            }
            this.apiUrl = HttpUrl.parse(baseUrl);
            return this;
        }

        public Builder baseurl(BaseUrl baseUrl) {
            this.baseUrl = Preconditions.checkNotNull(baseUrl,
                    BaseUrl.class.getCanonicalName() + "can not be null.");
            return this;
        }

        /**
         * 设置本地缓存文件 像 Glide
         *
         * @param cacheFile cacheFile
         */
        public Builder cacheFile(File cacheFile) {
            this.cacheFile = cacheFile;
            return this;
        }

        /**
         * 设置图片请求策略
         *
         * @param loaderStrategy {@link BaseImageLoaderStrategy}
         */
        public Builder imageLoaderStrategy(BaseImageLoaderStrategy loaderStrategy) {
            this.loaderStrategy = loaderStrategy;
            return this;
        }

        public Builder retrofitConfiguration(RetrofitConfig.RetrofitConfiguration retrofitConfiguration) {
            this.retrofitConfiguration = retrofitConfiguration;
            return this;
        }

        public Builder okhttpConfiguration(RetrofitConfig.OkhttpConfiguration okhttpConfiguration) {
            this.okhttpConfiguration = okhttpConfiguration;
            return this;
        }

        /**
         * 设置网络请求前后 对发生  和  返回数据进行处理
         *
         * @param handler GlobalHttpHandler
         * @return Builder
         */
        public Builder globalHttpHandler(GlobalHttpHandler handler) {
            this.handler = handler;
            return this;
        }

        /**
         * 设置全局的RxJava 错误处理
         *
         * @param responseErrorListener
         * @return
         */
        public Builder responseErrorListener(ResponseErrorListener responseErrorListener) {
            this.responseErrorListener = responseErrorListener;
            return this;
        }

        public Builder printHttpLogLevel(
                RequestInterceptor.Level printHttpLogLevel) {//是否让框架打印 Http 的请求和响应信息
            this.printHttpLogLevel = Preconditions.checkNotNull(printHttpLogLevel,
                    "The printHttpLogLevel can not be null, use RequestInterceptor.Level.NONE instead.");
            return this;
        }

        public Builder formatPrinter(FormatPrinter formatPrinter) {
            this.formatPrinter = Preconditions.checkNotNull(formatPrinter,
                    FormatPrinter.class.getCanonicalName() + "can not be null.");
            return this;
        }

        public GlobalConfigModule build() {
            return new GlobalConfigModule(this);
        }
    }

}
