package com.study.kotlin.message.service

import com.study.kotlin.base.data.net.RetrofitFactory
import com.study.kotlin.base.ext.convert
import com.study.kotlin.message.data.api.MessageApi
import com.study.kotlin.message.data.protocol.Message
import rx.Observable

class MessageService {

    /**
     * 获取消息列表
     */
    fun getMessageList(): Observable<MutableList<Message>?> {

        return RetrofitFactory.instance.create(MessageApi::class.java)
            .getMessageList()
            .convert()

    }
}