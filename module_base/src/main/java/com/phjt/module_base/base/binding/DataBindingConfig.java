package com.phjt.module_base.base.binding;

import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

/**
 * @author: gengyan
 * date:    2021/9/8 16:33
 * company: GY
 * description:  将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
 * 通过这样的方式，来彻底解决 视图调用的一致性问题，
 */
public class DataBindingConfig {

    private final int layout;

    private final int vmVariableId;

    private final ViewModel stateViewModel;

    private final SparseArray<Object> bindingParams = new SparseArray();

    public DataBindingConfig(@NonNull Integer layout, @NonNull Integer vmVariableId, @NonNull ViewModel stateViewModel) {
        this.layout = layout;
        this.vmVariableId = vmVariableId;
        this.stateViewModel = stateViewModel;
    }

    public int getLayout() {
        return this.layout;
    }

    public int getVmVariableId() {
        return this.vmVariableId;
    }

    public ViewModel getStateViewModel() {
        return this.stateViewModel;
    }

    public SparseArray<Object> getBindingParams() {
        return this.bindingParams;
    }

    public DataBindingConfig addBindingParam(@NonNull Integer variableId, @NonNull Object object) {
        if (this.bindingParams.get(variableId) == null) {
            this.bindingParams.put(variableId, object);
        }

        return this;
    }
}
