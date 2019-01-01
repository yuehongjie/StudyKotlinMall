package com.study.kotlin.base.data.protocol

/**
 * 基础响应类
 */

class BaseResp<T> (val status: Int, val message: String, val data:T)