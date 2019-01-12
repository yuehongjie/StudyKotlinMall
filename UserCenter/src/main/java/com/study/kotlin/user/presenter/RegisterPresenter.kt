package com.study.kotlin.user.presenter


import com.study.kotlin.base.ext.execute
import com.study.kotlin.base.presenter.BasePresenter
import com.study.kotlin.base.rx.BaseSubscriber
import com.study.kotlin.user.presenter.view.RegisterView
import com.study.kotlin.user.service.UserService
import javax.inject.Inject


// 使用 @Inject 的方式注解构造函数提供实例
class RegisterPresenter @Inject constructor(): BasePresenter<RegisterView>() {

    @Inject
    lateinit var userService: UserService

    fun register(mobile: String, verifyCode: String, pwd: String){

        if (!isNetWorkAvailable()) {
            return
        }
        //加载弹窗，弹窗的取消在 BaseSubscriber 中做了处理
        mView.showLoading()

        userService.register(mobile, verifyCode, pwd)
            .execute(object :BaseSubscriber<Boolean> (mView){

                override fun onNext(t: Boolean) {
                    if (t) {
                        mView.registerResult("注册成功")
                    }

                }



            })


/*
        //省略 userServiceImpl 直接使用 UserRepository 调用 Api

        UserRepository().register(mobile, verifyCode, pwd)
            .execute(object :BaseSubscriber<BaseResp<String>> () {

                override fun onNext(t: BaseResp<String>) {
                    Log.e("RegisterPresenter", "$t")
                    mView.registerResult(t.status == 0)
                }

            })

*/






    }

}