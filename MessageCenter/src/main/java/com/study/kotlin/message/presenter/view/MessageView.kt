package com.study.kotlin.message.presenter.view

import com.study.kotlin.base.presenter.view.BaseView
import com.study.kotlin.message.data.protocol.Message

interface MessageView:BaseView {
    fun onGetMessageListResult(result: MutableList<Message>?)
}