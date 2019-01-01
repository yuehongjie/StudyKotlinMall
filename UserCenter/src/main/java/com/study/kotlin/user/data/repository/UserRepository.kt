package com.study.kotlin.user.data.repository

import com.study.kotlin.base.data.net.RetrofitFactory
import com.study.kotlin.base.data.protocol.BaseResp
import com.study.kotlin.user.data.api.UserApi
import com.study.kotlin.user.data.protocol.RegisterReq
import retrofit2.Retrofit
import rx.Observable

class UserRepository {

    fun register(mobile: String, verifyCode: String, pwd: String): Observable<BaseResp<String>> {

        return RetrofitFactory.instance.create(UserApi::class.java)
            .register(RegisterReq(mobile, verifyCode, pwd))

    }

}