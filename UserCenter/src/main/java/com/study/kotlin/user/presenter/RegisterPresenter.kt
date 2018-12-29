package com.study.kotlin.user.presenter

import com.study.kotlin.base.presenter.BasePresenter
import com.study.kotlin.user.presenter.view.RegisterView

class RegisterPresenter: BasePresenter<RegisterView>() {

    fun register(){

        // 业务逻辑
        mView.registerResult()

    }

}