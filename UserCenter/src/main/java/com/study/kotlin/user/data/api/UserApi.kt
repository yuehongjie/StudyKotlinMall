package com.study.kotlin.user.data.api

import com.study.kotlin.base.data.protocol.BaseResp
import com.study.kotlin.user.data.protocol.ForgetPwdReq
import com.study.kotlin.user.data.protocol.LoginReq
import com.study.kotlin.user.data.protocol.RegisterReq
import com.study.kotlin.user.data.protocol.UserInfo
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import rx.Observable

interface UserApi {

    @POST("userCenter/register")
    fun register(@Body req: RegisterReq): Observable<BaseResp<String>>

    @POST("userCenter/login")
    fun login(@Body req: LoginReq): Observable<BaseResp<UserInfo>>

    @POST("userCenter/forgetPwd")
    fun forgetPwd(@Body req: ForgetPwdReq): Observable<BaseResp<String>>

}