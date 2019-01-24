package com.study.kotlin.goods.injection.component

import com.study.kotlin.base.injection.component.ActivityComponent
import com.study.kotlin.base.injection.scope.PerComponentScope
import com.study.kotlin.goods.injection.module.CategoryModule
import com.study.kotlin.goods.ui.fragment.CategoryFragment
import dagger.Component

@PerComponentScope
@Component(modules = [CategoryModule::class], dependencies = [ActivityComponent::class])
interface CategoryComponent {

    fun inject(fragment: CategoryFragment)

}