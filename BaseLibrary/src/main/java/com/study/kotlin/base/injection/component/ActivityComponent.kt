package com.study.kotlin.base.injection.component

import android.app.Activity
import android.content.Context
import com.study.kotlin.base.injection.module.ActivityModule
import com.study.kotlin.base.injection.scope.ActivityScope
import dagger.Component

/**
 * Activity 级别的 Component 依赖于 App 级别的 Component
 */
@ActivityScope
@Component(modules = [ActivityModule::class], dependencies = [AppComponent::class])
interface ActivityComponent {

    // 暴露出 ActivityModule 中的 Activity
    fun activity(): Activity

    //暴露出 AppModule 中的 Context
    fun context(): Context


}