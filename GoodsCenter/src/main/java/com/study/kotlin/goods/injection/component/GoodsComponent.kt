package com.study.kotlin.goods.injection.component

import com.study.kotlin.base.injection.component.ActivityComponent
import com.study.kotlin.base.injection.scope.PerComponentScope
import com.study.kotlin.goods.injection.module.GoodsModule
import com.study.kotlin.goods.ui.activity.GoodsListActivity
import dagger.Component

@PerComponentScope
@Component(modules = [GoodsModule::class], dependencies = [ActivityComponent::class])
interface GoodsComponent {

    fun inject(activity: GoodsListActivity)

}