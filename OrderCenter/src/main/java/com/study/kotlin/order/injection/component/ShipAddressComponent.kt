package com.study.kotlin.order.injection.component

import com.study.kotlin.base.injection.component.ActivityComponent
import com.study.kotlin.base.injection.scope.PerComponentScope
import com.study.kotlin.order.injection.module.ShipAddressModule
import com.study.kotlin.order.ui.activity.ShipAddressActivity
import com.study.kotlin.order.ui.activity.ShipAddressEditActivity
import dagger.Component

@PerComponentScope
@Component(modules = [ShipAddressModule::class], dependencies = [ActivityComponent::class])
interface ShipAddressComponent {

    fun inject(activity: ShipAddressEditActivity)

    fun inject(activity: ShipAddressActivity)
}