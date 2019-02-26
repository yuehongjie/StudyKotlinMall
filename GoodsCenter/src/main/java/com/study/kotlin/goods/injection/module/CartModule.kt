package com.study.kotlin.goods.injection.module

import com.study.kotlin.goods.service.CartService
import dagger.Module
import dagger.Provides

//购物车

@Module
class CartModule {

    @Provides
    fun provideCartService(): CartService {
        return CartService()
    }

}