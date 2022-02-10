package com.phjt.mvvmframe.model.bean

/**
 * @author: gengyan
 * date:    2019/11/1 13:40
 * company: GY
 * description: 基类
 */
class BaseBean<T> {

    var code = 0
    var msg: String? = null
    var data: T? = null

    val isOk: Boolean
        get() = code == 0
}