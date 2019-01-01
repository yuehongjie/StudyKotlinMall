package com.study.kotlin.user.data.protocol

//注册请求
data class RegisterReq(val mobile: String, val verifyCode: String, val pwd: String)