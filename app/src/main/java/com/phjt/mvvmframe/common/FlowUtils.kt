package com.phjt.mvvmframe.common

import androidx.lifecycle.MutableLiveData
import com.phjt.module_base.utils.log.LogUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import java.lang.NullPointerException


/**
 * @author: gengyan
 * date:    2021/9/18
 * company: GY
 * description: ViewModel 中 模板代码抽取
 */

fun <T> Flow<T>.applyFlowWithLoading(
    flowDate: Flow<T>,
    isShowLoading: MutableLiveData<Boolean>
): Flow<T> =
    flowDate.onStart {
        isShowLoading.value = true
        LogUtils.e("================onStart")
    }.onCompletion {
        isShowLoading.value = false
        LogUtils.e("================onCompletion")
    }.catch {
        LogUtils.e("================Throwable:: $it")
        ResponseErrorHandler.handleResponseError(it)
    }


fun <T> Flow<T>.applyFlow(flowDate: Flow<T>): Flow<T> =
    flowDate.catch {
        LogUtils.e("================Throwable:: $it")
        ResponseErrorHandler.handleResponseError(it)
    }