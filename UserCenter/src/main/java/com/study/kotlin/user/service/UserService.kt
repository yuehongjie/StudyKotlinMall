package com.study.kotlin.user.service


import com.study.kotlin.base.data.net.RetrofitFactory
import com.study.kotlin.base.ext.convertBoolean
import com.study.kotlin.user.data.api.UserApi
import com.study.kotlin.user.data.protocol.RegisterReq
import rx.Observable

class UserService {


    fun register(mobile: String, verifyCode: String, pwd: String): Observable<Boolean> {

        return RetrofitFactory.instance.create(UserApi::class.java)
            .register(RegisterReq(mobile, verifyCode, pwd))
            .convertBoolean()

    }

}