package com.study.kotlin.user.presenter

import com.study.kotlin.base.presenter.BasePresenter
import com.study.kotlin.user.presenter.view.ResetPwdView
import com.study.kotlin.user.service.UserService
import javax.inject.Inject

class ResetPwdPresenter: BasePresenter<ResetPwdView> () {

    @Inject
    lateinit var userService: UserService

    fun resetPwd(mobile: String, verifyCode: String) {

        if (!isNetWorkAvailable()) {
            return
        }

        mView.showLoading()



    }

}