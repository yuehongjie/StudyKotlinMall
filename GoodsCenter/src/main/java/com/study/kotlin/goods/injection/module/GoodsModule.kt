package com.study.kotlin.goods.injection.module

import com.study.kotlin.goods.service.GoodsService
import dagger.Module
import dagger.Provides

@Module
class GoodsModule {

    @Provides
    fun provideGoodsService(): GoodsService {

        return GoodsService()

    }

}