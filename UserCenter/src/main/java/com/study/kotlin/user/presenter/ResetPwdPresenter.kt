package com.study.kotlin.user.presenter

import com.study.kotlin.base.ext.execute
import com.study.kotlin.base.presenter.BasePresenter
import com.study.kotlin.base.rx.BaseSubscriber
import com.study.kotlin.user.presenter.view.ResetPwdView
import com.study.kotlin.user.service.UserService
import javax.inject.Inject

class ResetPwdPresenter @Inject constructor(): BasePresenter<ResetPwdView> () {

    @Inject
    lateinit var userService: UserService

    fun resetPwd(mobile: String, pwd: String) {

        if (!isNetWorkAvailable()) {
            return
        }

        mView.showLoading()

        userService.resetPwd(mobile, pwd)
            .execute(object : BaseSubscriber<Boolean> (mView) {

                override fun onNext(result: Boolean) {

                    if (result) {
                        mView.resetPwdResult("重置密码成功")
                    }
                }

            })


    }

}