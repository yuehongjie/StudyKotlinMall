package com.study.kotlin.order.injection.module

import com.study.kotlin.order.service.ShipAddressService
import dagger.Module
import dagger.Provides

@Module
class ShipAddressModule {

    @Provides
    fun provideOrderService():ShipAddressService  {
        return ShipAddressService()
    }

}