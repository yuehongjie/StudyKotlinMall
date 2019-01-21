package com.study.kotlin.user.injection.component

import com.study.kotlin.base.injection.component.ActivityComponent
import com.study.kotlin.base.injection.scope.PerComponentScope
import com.study.kotlin.user.injection.module.UploadModule
import com.study.kotlin.user.injection.module.UserModule
import com.study.kotlin.user.ui.activity.*
import dagger.Component


/**
 * 用户级别的 Component 依赖于 Activity 的 Component
 */
@PerComponentScope
@Component(modules = [ UserModule::class, UploadModule::class ], dependencies = [ActivityComponent::class])
interface UserComponent {

    fun inject(activity: RegisterActivity)

    fun inject(activity: LoginActivity)

    fun inject(activity: ForgetPwdActivity)

    fun inject(activity: ResetPwdActivity)

    fun inject(activity: UserInfoActivity)

}