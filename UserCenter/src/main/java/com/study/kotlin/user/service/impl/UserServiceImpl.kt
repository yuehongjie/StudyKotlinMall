package com.study.kotlin.user.service.impl


import com.study.kotlin.base.ext.convertBoolean
import com.study.kotlin.user.data.repository.UserRepository
import com.study.kotlin.user.service.UserService
import rx.Observable
import javax.inject.Inject

class UserServiceImpl @Inject constructor(): UserService {

    @Inject
    lateinit var repository: UserRepository

    override fun register(mobile: String, verifyCode: String, pwd: String): Observable<Boolean> {

        return repository.register(mobile, verifyCode, pwd)
            .convertBoolean()

    }

}