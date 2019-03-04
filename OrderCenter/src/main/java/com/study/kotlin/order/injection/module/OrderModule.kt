package com.study.kotlin.order.injection.module

import com.study.kotlin.order.service.OrderService
import dagger.Module
import dagger.Provides

@Module
class OrderModule {

    @Provides
    fun provideOrderService(): OrderService {
        return OrderService()
    }

}