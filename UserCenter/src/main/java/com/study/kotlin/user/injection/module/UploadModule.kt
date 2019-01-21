package com.study.kotlin.user.injection.module

import android.util.Log
import com.study.kotlin.user.service.UploadService
import dagger.Module
import dagger.Provides

//使用 module 的方式提供对象实例
@Module
class UploadModule {


    @Provides
    fun providesUploadService(): UploadService {


        Log.e("UploadModule", "providesUploadService")

        return UploadService()

    }

}