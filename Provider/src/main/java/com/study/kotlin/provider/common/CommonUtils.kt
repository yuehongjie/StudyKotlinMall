package com.study.kotlin.provider.common

import com.alibaba.android.arouter.launcher.ARouter
import com.study.kotlin.base.utils.AppPrefsUtils
import com.study.kotlin.provider.router.RouterPath

//顶层函数

fun isLogin(): Boolean {

    return AppPrefsUtils.getString(ProviderConstant.KEY_SP_TOKEN).isNotEmpty()

}

//封装登录操作，直接处理登陆后的逻辑，未登录的先登录
fun afterLogin(method: () -> Unit) {

    if (isLogin()) {

        method()

    }else{

        //使用 Arouter 跨模块跳转
        ARouter.getInstance().build(RouterPath.UserCenter.PATH_LOGIN).navigation()

    }

}