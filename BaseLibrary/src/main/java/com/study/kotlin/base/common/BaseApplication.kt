package com.study.kotlin.base.common

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.study.kotlin.base.injection.component.AppComponent
import com.study.kotlin.base.injection.component.DaggerAppComponent
import com.study.kotlin.base.injection.module.AppModule

open class BaseApplication : Application() {


    //提供 App 级别的 appComponent 的实例
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        context = this

        initAppInjection()

        initARouter()

    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
    private fun initAppInjection() {

        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()

    }

    private fun initARouter() {
        //if (isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()      // 打印日志
            ARouter.openDebug()    // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
       // }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化
    }


    companion object {
        lateinit var context:BaseApplication
    }

}