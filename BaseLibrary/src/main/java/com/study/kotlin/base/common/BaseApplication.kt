package com.study.kotlin.base.common

import android.app.Application
import android.content.Context
import com.study.kotlin.base.injection.component.AppComponent
import com.study.kotlin.base.injection.component.DaggerAppComponent
import com.study.kotlin.base.injection.module.AppModule

class BaseApplication : Application() {


    //提供 App 级别的 appComponent 的实例
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        context = this

        initAppInjection()

    }

    private fun initAppInjection() {

        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()

    }


    companion object {
        lateinit var context:BaseApplication
    }

}