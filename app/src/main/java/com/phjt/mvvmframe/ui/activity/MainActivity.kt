package com.phjt.mvvmframe.ui.activity

import com.phjt.module_base.base.BaseActivity
import com.phjt.module_base.base.binding.DataBindingConfig
import com.phjt.mvvmframe.BR
import com.phjt.mvvmframe.R
import com.phjt.mvvmframe.vm.state.MainViewModel

class MainActivity : BaseActivity<MainViewModel>() {

    private var mainViewModel: MainViewModel? = null

    override fun initViewModel() {
        mainViewModel = getActivityScopeViewModel(MainViewModel::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.activity_main, BR.vm, mainViewModel!!)
    }

    override fun getStateViewModel(): MainViewModel? {
        return mainViewModel
    }

    override fun initData() {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }


}