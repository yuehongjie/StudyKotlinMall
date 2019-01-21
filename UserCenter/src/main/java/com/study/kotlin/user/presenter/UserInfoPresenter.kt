package com.study.kotlin.user.presenter

import com.study.kotlin.base.ext.execute
import com.study.kotlin.base.presenter.BasePresenter
import com.study.kotlin.base.rx.BaseSubscriber
import com.study.kotlin.user.data.protocol.UserInfo
import com.study.kotlin.user.presenter.view.UserInfoView
import com.study.kotlin.user.service.UploadService
import com.study.kotlin.user.service.UserService
import javax.inject.Inject

class UserInfoPresenter @Inject constructor(): BasePresenter<UserInfoView>() {

    @Inject
    lateinit var uploadService:UploadService

    @Inject
    lateinit var userService: UserService

    fun getUploadToken() {
        if (!isNetWorkAvailable()) {
            return
        }

        mView.showLoading()
        uploadService.getUploadToken()
            .execute(object : BaseSubscriber<String>(mView){
                override fun onNext(result: String) {

                    mView.onGetUploadTokenResult(result)

                }
            })
    }

    fun editUser(userIcon: String, userName: String, gender: String, sign: String) {

        if (!isNetWorkAvailable()) {
            return
        }

        mView.showLoading()
        userService.editUser(userIcon, userName, gender, sign)
            .execute(object: BaseSubscriber<UserInfo>(mView) {

                override fun onNext(result: UserInfo) {

                    mView.onEditUserResult(result)

                }

            })

    }

}