package com.study.kotlin.message.injection.component

import com.study.kotlin.base.injection.component.ActivityComponent
import com.study.kotlin.base.injection.scope.PerComponentScope
import com.study.kotlin.message.injection.module.MessageModule
import com.study.kotlin.message.ui.fragment.MessageFragment
import dagger.Component

@PerComponentScope
@Component(modules = [MessageModule::class], dependencies = [ActivityComponent::class])
interface MessageComponent {

    fun inject(fragment: MessageFragment)

}