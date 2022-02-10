package com.phjt.mvvmframe.model.repository.impl


import com.phjt.module_base.base.BaseRepository
import com.phjt.module_base.http.RetrofitConfig
import com.phjt.module_base.utils.log.LogUtils
import com.phjt.mvvmframe.model.api.ApiService
import com.phjt.mvvmframe.model.bean.BaseBean
import com.phjt.mvvmframe.model.bean.PopularArticleListBean
import com.phjt.mvvmframe.model.repository.ITestRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.NullPointerException

/**
 * @author: gengyan
 * date:    2021/9/14 18:33
 * company: GY
 * description: 数据仓库的实现
 * 在内部持有 网络层 和 数据库层的实例，进行数据的获取及传递
 * <p>
 * Repository应该作为数据处理中的原子操作，对于较为复杂的业务逻辑，应该在Repository与VM中新增Domain层，去处理
 * <p>
 * ViewModel中可以持有多个RepositoryImpl实例，对于业务逻辑复用的情况可对Repository进行拆分
 * 同理，对于视图状态都能复用的情况，可拆分ViewModel进行复用，视图控制器即持有多个VM的引用
 */
class TestRepositoryImpl : BaseRepository(), ITestRepository {

    //使用Flow进行网络请求，发送数据，在ViewModel中进行接收
    //注意区分 flowof() 与 flow{}
    override suspend fun mobileLogin(mobile: String, password: String): Flow<BaseBean<String>> {
        return flow {
            emit(mRetrofit.create(ApiService::class.java).mobileLogin(mobile, password))
        }.flowOn(Dispatchers.IO)

    }

    override suspend fun getArticles(): Flow<BaseBean<List<PopularArticleListBean>>> {
        return flow {
            val data = mRetrofit.create(ApiService::class.java).getArticles()
            emit(data)
        }.flowOn(Dispatchers.IO)
    }

}