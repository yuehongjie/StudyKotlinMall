package com.study.kotlin.base.injection.component

import android.content.Context
import com.study.kotlin.base.injection.module.AppModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    //暴露出 AppModule 中的 Context
    fun context():Context

}