package com.phjt.module_base.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.phjt.module_base.base.binding.DataBindActivity;

/**
 * @author: gengyan
 * date:    2021/9/8 17:46
 * company: GY
 * description: 描述
 */
public abstract class BaseActivity<V extends BaseViewModel> extends DataBindActivity {

    /**
     * 当前页面的state-ViewModel
     * 在{@link DataBindActivity#initViewModel}中进行初始化后获取
     *
     * @return {@link BaseViewModel}
     */
    protected abstract V getStateViewModel();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        observeLoading();

        initData();
    }

    /**
     * 用于统一控制页面{@link BaseActivity#showLoading()#hideLoading()}
     */
    private void observeLoading() {
        if (getStateViewModel() != null && getStateViewModel().isShowLoading != null) {
            getStateViewModel().isShowLoading.observe(this, isShowLoading -> {
                if (isShowLoading) {
                    showLoading();
                } else {
                    hideLoading();
                }
            });
        }
    }

    /**
     * 视图控制层中进行具体实现
     * 处理视图控制层逻辑
     */
    protected abstract void showLoading();

    protected abstract void hideLoading();

}
