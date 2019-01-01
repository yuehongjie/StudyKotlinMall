package com.study.kotlin.user.service.impl

import com.study.kotlin.base.data.protocol.BaseResp
import com.study.kotlin.base.rx.BaseException
import com.study.kotlin.user.data.repository.UserRepository
import com.study.kotlin.user.service.UserService
import rx.Observable
import rx.functions.Func1

class UserServiceImpl: UserService {

    override fun register(mobile: String, verifyCode: String, pwd: String): Observable<Boolean> {

        val repository = UserRepository()
        return repository.register(mobile, verifyCode, pwd)
            .flatMap(object :Func1<BaseResp<String>, Observable<Boolean>>{

                override fun call(t: BaseResp<String>): Observable<Boolean> {

                    if (t.status != 0) {
                        return Observable.error(BaseException(t.status, t.message))
                    }

                    return Observable.just(true)

                }

            })

    }

}