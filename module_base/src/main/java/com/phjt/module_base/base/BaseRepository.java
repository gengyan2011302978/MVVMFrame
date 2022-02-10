package com.phjt.module_base.base;

import com.phjt.module_base.http.RetrofitConfig;

import retrofit2.Retrofit;

/**
 * @author: gengyan
 * date:    2021/9/17 18:48
 * company: GY
 * description: 描述
 */
public abstract class BaseRepository {

    protected final Retrofit mRetrofit = RetrofitConfig.getInstance().getRetrofit();
}
