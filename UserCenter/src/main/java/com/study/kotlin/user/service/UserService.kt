package com.study.kotlin.user.service


import com.study.kotlin.base.data.net.RetrofitFactory
import com.study.kotlin.base.ext.convert
import com.study.kotlin.base.ext.convertBoolean
import com.study.kotlin.user.data.api.UserApi
import com.study.kotlin.user.data.protocol.ForgetPwdReq
import com.study.kotlin.user.data.protocol.LoginReq
import com.study.kotlin.user.data.protocol.RegisterReq
import com.study.kotlin.user.data.protocol.UserInfo
import rx.Observable

class UserService {


    fun register(mobile: String, verifyCode: String, pwd: String): Observable<Boolean> {

        return RetrofitFactory.instance.create(UserApi::class.java)
            .register(RegisterReq(mobile, verifyCode, pwd))
            .convertBoolean()

    }

    fun login(mobile: String, pwd: String, pushId: String): Observable<UserInfo> {

        return RetrofitFactory.instance.create(UserApi::class.java)
            .login(LoginReq(mobile, pwd, pushId))
            .convert()

    }

    fun forgetPwd(mobile: String, verifyCode: String):Observable<Boolean> {

        return RetrofitFactory.instance.create(UserApi::class.java)
            .forgetPwd(ForgetPwdReq(mobile, verifyCode))
            .convertBoolean()

    }

}