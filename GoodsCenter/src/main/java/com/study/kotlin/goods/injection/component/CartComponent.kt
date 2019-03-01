package com.study.kotlin.goods.injection.component

import com.study.kotlin.base.injection.component.ActivityComponent
import com.study.kotlin.base.injection.scope.PerComponentScope
import com.study.kotlin.goods.injection.module.CartModule
import com.study.kotlin.goods.ui.fragment.CartListFragment
import dagger.Component

@PerComponentScope
@Component(modules = [CartModule::class], dependencies = [ActivityComponent::class])
interface CartComponent {

    fun inject(fragment: CartListFragment)

}