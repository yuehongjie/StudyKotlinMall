package com.study.kotlin.pay.injection.module

import com.study.kotlin.pay.service.PayService
import dagger.Module
import dagger.Provides

@Module
class PayModule {

    @Provides
    fun providePayService(): PayService {
        return PayService()
    }

}