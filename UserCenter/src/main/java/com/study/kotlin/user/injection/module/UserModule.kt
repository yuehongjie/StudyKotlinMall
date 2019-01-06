package com.study.kotlin.user.injection.module

import android.util.Log
import com.study.kotlin.user.service.UserService
import com.study.kotlin.user.service.impl.UserServiceImpl
import dagger.Module
import dagger.Provides

//使用 module 的方式提供对象实例
@Module
class UserModule {


    //使用 UserServiceImpl() 创建实例
//    fun providesUserService(): UserService {
//
//        return UserServiceImpl()
//
//    }

    //这里需要 UserServiceImpl ,可以使用 Inject 注解 UserServiceImpl 构造函数
    @Provides
    fun providesUserService(service: UserServiceImpl): UserService {


        Log.e("UserModule", "providesUserService")

        return service

    }

}