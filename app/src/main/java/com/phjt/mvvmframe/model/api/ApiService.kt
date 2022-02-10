package com.phjt.mvvmframe.model.api

import com.phjt.mvvmframe.model.bean.BaseBean
import com.phjt.mvvmframe.model.bean.PopularArticleListBean
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * @author: gengyan
 * date:    2021/9/14 18:26
 * company: GY
 * description: 描述
 */
interface ApiService {

    @POST("article/v1.0.2/popularArticle")
    suspend fun getArticles(): BaseBean<List<PopularArticleListBean>>

    @FormUrlEncoded
    @POST("login/v1.0.0/doLogin")
    suspend fun mobileLogin(
        @Field("mobile") mobile: String,
        @Field("password") password: String
    ): BaseBean<String>
}