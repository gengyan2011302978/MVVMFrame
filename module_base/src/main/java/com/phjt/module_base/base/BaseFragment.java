package com.phjt.module_base.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.phjt.module_base.base.binding.DataBindingFragment;

import org.jetbrains.annotations.NotNull;

/**
 * @author: gengyan
 * date:    2021/9/8 17:53
 * company: GY
 * description: 描述
 */
public abstract class BaseFragment<V extends BaseViewModel> extends DataBindingFragment {

    /**
     * 当前页面的state-ViewModel
     * 在{@link DataBindingFragment#initViewModel}中进行初始化后获取
     *
     * @return {@link BaseViewModel}
     */
    protected abstract V getStateViewModel();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        observeLoading();

        initData();
    }

    /**
     * 控制{@link BaseFragment#showLoading()#hideLoading()}
     */
    private void observeLoading() {
        if (getStateViewModel() != null && getStateViewModel().isShowLoading != null) {
            getStateViewModel().isShowLoading.observe(getViewLifecycleOwner(), isShowLoading -> {
                if (isShowLoading) {
                    showLoading();
                } else {
                    hideLoading();
                }
            });
        }
    }

    protected abstract void showLoading();

    protected abstract void hideLoading();

//    protected NavController nav() {
//        return NavHostFragment.findNavController(this);
//    }

    protected Context getApplicationContext() {
        return mActivity.getApplicationContext();
    }
}
