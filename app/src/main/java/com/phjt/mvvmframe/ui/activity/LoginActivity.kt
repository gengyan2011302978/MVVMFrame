package com.phjt.mvvmframe.ui.activity

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.text.TextUtils
import android.widget.Toast
import com.phjt.module_base.base.BaseActivity
import com.phjt.module_base.base.binding.DataBindingConfig
import com.phjt.mvvmframe.BR
import com.phjt.mvvmframe.R
import com.phjt.mvvmframe.vm.state.LoginViewModel


/**
 * @author: gengyan
 * date:    2021/9/15 16:52
 * company: GY
 * description: 描述
 */
class LoginActivity : BaseActivity<LoginViewModel>() {

    var loginViewModel: LoginViewModel? = null
    var dialog: Dialog? = null

    override fun initViewModel() {
        loginViewModel = getActivityScopeViewModel(LoginViewModel::class.java)
    }

    override fun getStateViewModel(): LoginViewModel? {
        return loginViewModel
    }

    // 将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
    // 通过这样的方式，来彻底解决 视图实例 null 安全的一致性问题，
    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.activity_login, BR.vm, loginViewModel!!)
            .addBindingParam(BR.click, ClickProxy())
    }

    override fun initData() {
        //网络请求，数据返回观察者
        //对于VM中视图状态的赋值，应在视图控制器中编写，不在ViewModel中进行
        //XML中应避免业务逻辑的编码，仅存在最小粒度的赋值操作，把UI逻辑控制在视频控制器中，方便调试
        loginViewModel?.getSuccessData()?.observe(this) {
            if (it.isOk) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                showMessage(it.msg.toString())
            }
        }

//        测试图片加载
//        AppImageLoader.loadUrlFail(
//            "https://test-k8s-oss.peogoo.com/test-shangwuyou/images/9616251076843057.jpg",
//            binding.root.iv_test,R.mipmap.ic_launcher_round
//        )
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        dialog = AlertDialog.Builder(this)
            .setMessage("dialog showing ....")
            .show()
    }

    override fun hideLoading() {
        dialog?.dismiss()
    }

    inner class ClickProxy {

        fun clickLogin() {
            //通过 xml 中的双向绑定，使得能够通过 state-ViewModel 中与控件发生绑定的"可观察数据"拿到控件的数据，
            //避免了直接操作view
            val userName = loginViewModel?.userName?.value
            val password = loginViewModel?.password?.value

            when {
                TextUtils.isEmpty(userName) -> {
                    showMessage("请输入手机号")
                }
                TextUtils.isEmpty(password) -> {
                    showMessage("请输入密码")
                }
                else -> {
                    loginViewModel?.mobileLogin(userName!!, password!!)
                }
            }
        }
    }
}