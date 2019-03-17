package com.study.kotlin.message.injection.module

import com.study.kotlin.message.service.MessageService
import dagger.Module
import dagger.Provides

@Module
class MessageModule {

    @Provides
    fun provideMessageService(): MessageService {
        return MessageService()
    }
}