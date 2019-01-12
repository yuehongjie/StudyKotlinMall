package com.study.kotlin.user.presenter


import com.study.kotlin.base.ext.execute
import com.study.kotlin.base.presenter.BasePresenter
import com.study.kotlin.base.rx.BaseSubscriber
import com.study.kotlin.user.data.protocol.UserInfo
import com.study.kotlin.user.presenter.view.LoginView
import com.study.kotlin.user.service.UserService
import javax.inject.Inject


// 使用 @Inject 的方式注解构造函数提供实例
class LoginPresenter @Inject constructor(): BasePresenter<LoginView>() {

    @Inject
    lateinit var userService: UserService

    fun login(mobile: String, pwd: String, pushId: String){

        if (!isNetWorkAvailable()) {
            return
        }
        //加载弹窗，弹窗的取消在 BaseSubscriber 中做了处理
        mView.showLoading()

        userService.login(mobile, pwd, pushId)
            .execute(object : BaseSubscriber<UserInfo>(mView){

                override fun onNext(result: UserInfo) {
                    mView.loginResult(result)
                }

            })

    }

}