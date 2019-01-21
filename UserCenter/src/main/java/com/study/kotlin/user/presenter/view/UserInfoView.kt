package com.study.kotlin.user.presenter.view

import com.study.kotlin.base.presenter.view.BaseView
import com.study.kotlin.user.data.protocol.UserInfo

interface UserInfoView :BaseView{

    fun onGetUploadTokenResult(result: String)

    fun onEditUserResult(result: UserInfo)
}