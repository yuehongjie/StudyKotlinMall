package com.study.kotlin.user.data.protocol

//登录请求
data class LoginReq(val mobile: String, val pwd: String, val pushId: String)