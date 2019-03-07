package com.study.kotlin.order.injection.component

import com.study.kotlin.base.injection.component.ActivityComponent
import com.study.kotlin.base.injection.scope.PerComponentScope
import com.study.kotlin.order.injection.module.OrderModule
import com.study.kotlin.order.ui.activity.OrderConfirmActivity
import com.study.kotlin.order.ui.activity.OrderDetailActivity
import com.study.kotlin.order.ui.fragment.OrderFragment
import dagger.Component

@PerComponentScope
@Component(modules = [OrderModule::class], dependencies = [ActivityComponent::class])
interface OrderComponent {

    fun inject(activity: OrderConfirmActivity)

    fun inject(fragment: OrderFragment)

    fun inject(activity: OrderDetailActivity)
}