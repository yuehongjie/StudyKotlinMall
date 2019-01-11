package com.study.kotlin.user.injection.module

import android.util.Log
import com.study.kotlin.user.service.UserService
import dagger.Module
import dagger.Provides

//使用 module 的方式提供对象实例
@Module
class UserModule {


    //使用 UserService() 创建实例
//    fun providesUserService(): UserService {
//
//        return UserService()
//
//    }

    //这里需要 UserService ,可以使用 Inject 注解 UserService 构造函数
    @Provides
    fun providesUserService(): UserService {


        Log.e("UserModule", "providesUserService")

        return UserService()

    }

}