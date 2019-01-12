package com.study.kotlin.user.presenter

import com.study.kotlin.base.ext.execute
import com.study.kotlin.base.presenter.BasePresenter
import com.study.kotlin.base.rx.BaseSubscriber
import com.study.kotlin.user.presenter.view.ForgetPwdView
import com.study.kotlin.user.service.UserService
import javax.inject.Inject

class ForgetPwdPresenter @Inject constructor(): BasePresenter<ForgetPwdView> () {

    @Inject
    lateinit var userService: UserService

    fun forgetPwd(mobile: String, verifyCode: String) {

        if (!isNetWorkAvailable()) {
            return
        }

        mView.showLoading()

        userService.forgetPwd(mobile, verifyCode)
            .execute(object: BaseSubscriber<Boolean>(mView) {
                override fun onNext(result: Boolean) {

                    if (result) {
                        mView.forgetPwdResult("手机号验证成功")
                    }

                }
            })


    }

}