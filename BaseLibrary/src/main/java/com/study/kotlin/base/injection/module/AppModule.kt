package com.study.kotlin.base.injection.module

import android.content.Context
import com.study.kotlin.base.common.BaseApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * App 级别的 Module 一般提供"单例"对象
 */

@Module
class AppModule(private val context: BaseApplication) {


    @Provides
    @Singleton
    fun providesContext():Context {

        return context

    }

}