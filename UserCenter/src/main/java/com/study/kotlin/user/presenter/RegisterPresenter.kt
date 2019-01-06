package com.study.kotlin.user.presenter

import android.util.Log
import com.study.kotlin.base.data.protocol.BaseResp
import com.study.kotlin.base.ext.execute
import com.study.kotlin.base.presenter.BasePresenter
import com.study.kotlin.base.rx.BaseSubscriber
import com.study.kotlin.user.data.repository.UserRepository
import com.study.kotlin.user.presenter.view.RegisterView
import com.study.kotlin.user.service.UserService
import com.study.kotlin.user.service.impl.UserServiceImpl
import javax.inject.Inject


// 使用 @Inject 的方式注解构造函数提供实例
class RegisterPresenter @Inject constructor(): BasePresenter<RegisterView>() {

    @Inject
    lateinit var userService: UserService

    fun register(mobile: String, verifyCode: String, pwd: String){

        // 业务逻辑

        userService.register(mobile, verifyCode, pwd)
            .execute(object :BaseSubscriber<Boolean> (){

                override fun onNext(t: Boolean) {
                    if (t) {
                        mView.registerResult("注册成功")
                    }else {
                        mView.registerResult("注册失败")
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