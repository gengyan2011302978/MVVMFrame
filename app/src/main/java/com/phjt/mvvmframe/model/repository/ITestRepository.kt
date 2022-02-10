package com.phjt.mvvmframe.model.repository

import com.phjt.mvvmframe.model.bean.BaseBean
import com.phjt.mvvmframe.model.bean.PopularArticleListBean
import kotlinx.coroutines.flow.Flow

/**
 * @author: gengyan
 * date:    2021/9/15 14:17
 * company: GY
 * description: 描述
 */
interface ITestRepository {

    suspend fun mobileLogin(mobile: String, password: String): Flow<BaseBean<String>>

    suspend fun getArticles(): Flow<BaseBean<List<PopularArticleListBean>>>

}