package com.study.kotlin.user.service


import com.study.kotlin.base.data.net.RetrofitFactory
import com.study.kotlin.base.ext.convert
import com.study.kotlin.user.data.api.UploadApi
import rx.Observable

class UploadService {


    fun getUploadToken(): Observable<String> {

        return RetrofitFactory.instance.create(UploadApi::class.java)
            .getUploadToken()
            .convert()

    }

}