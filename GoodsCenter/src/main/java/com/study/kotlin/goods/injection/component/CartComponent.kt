package com.study.kotlin.goods.injection.component

import com.study.kotlin.goods.injection.module.CartModule
import dagger.Component


@Component(modules = [CartModule::class])
interface CartComponent {



}