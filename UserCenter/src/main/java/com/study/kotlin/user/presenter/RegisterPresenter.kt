package com.study.kotlin.user.presenter

import android.util.Log
import com.study.kotlin.base.data.protocol.BaseResp
import com.study.kotlin.base.ext.execute
import com.study.kotlin.base.presenter.BasePresenter
import com.study.kotlin.base.rx.BaseSubscriber
import com.study.kotlin.user.data.repository.UserRepository
import com.study.kotlin.user.presenter.view.RegisterView
import com.study.kotlin.user.service.impl.UserServiceImpl


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