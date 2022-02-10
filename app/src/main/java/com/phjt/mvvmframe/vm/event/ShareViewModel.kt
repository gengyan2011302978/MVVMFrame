package com.phjt.mvvmframe.vm.event

import androidx.lifecycle.ViewModel
import com.kunminx.architecture.ui.callback.UnPeekLiveData

/**
 * @author: gengyan
 * date:    2021/9/15 18:10
 * company: GY
 * description: 用于跨页面通信 申明不同生命周期的 event-ViewModel  (注意区分state-ViewModel)
 * 需要注意的是：LiveData粘性数据，会导致注册观察者的时候就收到之前发送的数据(生命周期！！！)
 */

object ShareViewModel : ViewModel() {

    //通过 UnPeekLiveData 配合 SharedViewModel 来发送 生命周期安全的，确保消息同步一致性和可靠性的 "跨页面" 通知。
    //网络请求中同样如此
    val shareData: UnPeekLiveData<String> = UnPeekLiveData()

}