package com.study.kotlin.goods.injection.module

import com.study.kotlin.goods.service.CategoryService
import dagger.Module
import dagger.Provides

@Module
class CategoryModule {

    @Provides
    fun provideCategoryService(): CategoryService {

        return CategoryService()

    }

}