package com.phjt.mvvmframe.vm.state

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kunminx.architecture.ui.callback.UnPeekLiveData
import com.phjt.module_base.base.BaseViewModel
import com.phjt.mvvmframe.R
import com.phjt.mvvmframe.common.applyFlowWithLoading
import com.phjt.mvvmframe.model.bean.BaseBean
import com.phjt.mvvmframe.model.repository.ITestRepository
import com.phjt.mvvmframe.model.repository.impl.TestRepositoryImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * @author: gengyan
 * date:    2021/9/15 16:53
 * company: GY
 * description: 每个页面都要单独准备一个 state-ViewModel
 *  来托管 DataBinding 绑定的临时状态，以及视图控制器重建时状态的恢复。
 * <p>
 *  state-ViewModel中仅限于 视图状态的管理托管 和 数据仓库发送数据的接收(viewModelScope)
 *  UI 逻辑只适合在 Activity/Fragment 等视图控制器中完成
 *  因为ViewModel不应该持有View的引用 (如果VM中处理UI逻辑，会不可避免)，避免了内存泄露
 */
class LoginViewModel : BaseViewModel() {

    var userName: MutableLiveData<String> = MutableLiveData("17744594282")

    var password: MutableLiveData<String> = MutableLiveData("gy123")

    var errorImg: ObservableField<Int> = ObservableField(R.mipmap.ic_launcher)


    //使用private 是为了添加访问控制的权限，控制Live Data的“读写分离”
    //在ViewModel中进行LiveData属性的赋值，在视图控制器中通过get方法添加观察者进行操作
    //避免在外部对LiveData属性进行修改
    private val successData: UnPeekLiveData<BaseBean<String>> = UnPeekLiveData()

    fun getSuccessData(): UnPeekLiveData<BaseBean<String>> = successData


    //持有外部数据仓库的实例，获取数据
    private val repository: ITestRepository = TestRepositoryImpl()

    //使用viewModelScope，控制协程的生命周期
    //如果 ViewModel 已清除，则在此范围内启动的协程都会自动取消,内部的Flow也会取消
    //从而取消网络请求，避免内存泄露、资源消耗、程序崩溃等
    fun mobileLogin(mobile: String, password: String) = viewModelScope.launch {
        repository.mobileLogin(mobile, password).run {
            applyFlowWithLoading(this, isShowLoading)
        }.collectLatest {
            successData.postValue(it)
        }
    }


}