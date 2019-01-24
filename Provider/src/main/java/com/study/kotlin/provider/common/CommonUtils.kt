package com.study.kotlin.provider.common

import com.kotlin.provider.common.ProviderConstant
import com.study.kotlin.base.utils.AppPrefsUtils

//顶层函数

fun isLogin(): Boolean {

    return AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_ICON).isNotEmpty()

}