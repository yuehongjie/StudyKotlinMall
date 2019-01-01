package com.study.kotlin.user.presenter

import com.study.kotlin.base.ext.execute
import com.study.kotlin.base.presenter.BasePresenter
import com.study.kotlin.base.rx.BaseSubscriber
import com.study.kotlin.user.presenter.view.RegisterView
import com.study.kotlin.user.service.impl.UserServiceImpl
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class RegisterPresenter: BasePresenter<RegisterView>() {

    fun register(mobile: String, verifyCode: String, pwd: String){

        // 业务逻辑

        val userServiceImpl = UserServiceImpl()
        userServiceImpl.register(mobile, verifyCode, pwd)
            .execute(object :BaseSubscriber<Boolean> (){

                override fun onNext(t: Boolean) {
                    mView.registerResult(t)
                }



            })



    }

}