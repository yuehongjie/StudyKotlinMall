package com.study.kotlin.message.presenter

import com.study.kotlin.base.ext.execute
import com.study.kotlin.base.presenter.BasePresenter
import com.study.kotlin.base.rx.BaseSubscriber
import com.study.kotlin.message.data.protocol.Message
import com.study.kotlin.message.presenter.view.MessageView
import com.study.kotlin.message.service.MessageService
import javax.inject.Inject

class MessagePresenter @Inject constructor() :BasePresenter<MessageView>() {

    @Inject
    lateinit var service: MessageService

    fun getMessageList() {
        if (!isNetWorkAvailable()) {
            return
        }

        mView.showLoading()

        service.getMessageList()
            .execute(object: BaseSubscriber<MutableList<Message>?>(mView) {
                override fun onNext(result: MutableList<Message>?) {
                    mView.onGetMessageListResult(result)
                }
            })

    }
}