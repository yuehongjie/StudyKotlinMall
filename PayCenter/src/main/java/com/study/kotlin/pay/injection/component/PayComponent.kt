package com.study.kotlin.pay.injection.component

import com.study.kotlin.base.injection.component.ActivityComponent
import com.study.kotlin.base.injection.scope.PerComponentScope
import com.study.kotlin.pay.injection.module.PayModule
import com.study.kotlin.pay.ui.activity.CashRegisterActivity
import dagger.Component

@PerComponentScope
@Component(modules = [PayModule::class], dependencies = [ActivityComponent::class])
interface PayComponent {

    fun inject(activity: CashRegisterActivity)

}