package com.phjt.mvvmframe.vm.state

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phjt.module_base.base.BaseViewModel
import com.phjt.mvvmframe.common.applyFlowWithLoading
import com.phjt.mvvmframe.model.bean.BaseBean
import com.phjt.mvvmframe.model.bean.PopularArticleListBean
import com.phjt.mvvmframe.model.repository.impl.TestRepositoryImpl
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import timber.log.Timber

/**
 * @author: gengyan
 * date:    2021/9/8 16:56
 * company: GY
 * description: 描述
 */
class MainViewModel : BaseViewModel() {

    val nickName = MutableLiveData<String>()

    private val repository = TestRepositoryImpl()

    fun getData() = viewModelScope.launch {
        repository.getArticles().run {
            applyFlowWithLoading(this, isShowLoading)
        }.collectLatest {
            nickName.postValue(it.code.toString())
            Timber.e("==============${it.code}")
        }
    }

}