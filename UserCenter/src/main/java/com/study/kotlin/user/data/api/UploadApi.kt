package com.study.kotlin.user.data.api

import com.study.kotlin.base.data.protocol.BaseResp
import retrofit2.http.POST
import rx.Observable

interface UploadApi {

    @POST("common/getUploadToken")
    fun getUploadToken(): Observable<BaseResp<String>>

}