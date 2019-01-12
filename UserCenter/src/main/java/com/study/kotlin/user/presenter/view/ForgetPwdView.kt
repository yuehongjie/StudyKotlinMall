package com.study.kotlin.user.presenter.view

import com.study.kotlin.base.presenter.view.BaseView
import com.study.kotlin.user.data.protocol.UserInfo

interface ForgetPwdView: BaseView {

    fun forgetPwdResult(result: String)

}