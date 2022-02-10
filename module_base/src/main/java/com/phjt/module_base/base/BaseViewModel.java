package com.phjt.module_base.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * @author: gengyan
 * date:    2021/9/17 18:56
 * company: GY
 * description: 描述
 */
public abstract class BaseViewModel extends ViewModel {

    public final MutableLiveData<Boolean> isShowLoading = new MutableLiveData<>();
}
