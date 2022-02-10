package com.phjt.module_base.base.binding;

import android.os.Bundle;
import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.phjt.module_base.base.BaseApplication;

/**
 * @author: gengyan
 * date:    2021/9/8 16:27
 * company: GY
 * description: 描述
 */
public abstract class DataBindActivity extends AppCompatActivity {

    private ViewDataBinding mBinding;

    private ViewModelProvider mActivityProvider;
    private ViewModelProvider mApplicationProvider;

    /**
     * ViewModel在此初始化
     */
    protected abstract void initViewModel();

    /**
     * 在此绑定 Layout与ViewModel
     *
     * @return {@link DataBindingConfig}
     */
    protected abstract DataBindingConfig getDataBindingConfig();

    /**
     * 非必要情况下，尽可能不在子类中拿到 binding 实例乃至获取 view 实例
     *
     * @return binding实例
     */
    protected ViewDataBinding getBinding() {
        return mBinding;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.initViewModel();

        DataBindingConfig dataBindingConfig = getDataBindingConfig();
        //将 DataBinding 实例限制于 base 页面中，默认不向子类暴露
        ViewDataBinding binding = DataBindingUtil.setContentView(this, dataBindingConfig.getLayout());
        binding.setVariable(dataBindingConfig.getVmVariableId(), dataBindingConfig.getStateViewModel());
        SparseArray<Object> bindingParams = dataBindingConfig.getBindingParams();
        for (int i = 0, length = bindingParams.size(); i < length; i++) {
            binding.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i));
        }
        binding.setLifecycleOwner(this);

        mBinding = binding;
    }


    //通过 "工厂模式" 来实现 ViewModel 的作用域可控
    //目前我们在项目中提供了 Application、Activity、Fragment 三个级别的作用域，
    //值得注意的是，通过不同作用域的 Provider 获得的 ViewModel 实例不是同一个，
    //所以如果 ViewModel 对状态信息的保留不符合预期，可以从这个角度出发去排查 是否眼前的 ViewModel 实例不是目标实例所致。
    protected <T extends ViewModel> T getActivityScopeViewModel(@NonNull Class<T> modelClass) {
        if (mActivityProvider == null) {
            mActivityProvider = new ViewModelProvider(this);
        }
        return mActivityProvider.get(modelClass);
    }

    protected <T extends ViewModel> T getApplicationScopeViewModel(@NonNull Class<T> modelClass) {
        if (mApplicationProvider == null) {
            mApplicationProvider = new ViewModelProvider((BaseApplication) this.getApplicationContext());
        }
        return mApplicationProvider.get(modelClass);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBinding != null) {
            mBinding.unbind();
            mBinding = null;
        }
    }
}
