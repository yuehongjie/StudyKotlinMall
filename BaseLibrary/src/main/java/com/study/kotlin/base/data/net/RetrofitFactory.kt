package com.study.kotlin.base.data.net

import android.util.Log
import com.study.kotlin.base.common.BaseConstant
import com.study.kotlin.base.utils.AppPrefsUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitFactory private constructor(){

    companion object {
        val instance: RetrofitFactory by lazy {
            RetrofitFactory()
        }
    }

    private var retrofit:Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BaseConstant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(initClient())
            .build()
    }

    private fun initClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(initHeaderInterceptor())
            .addInterceptor(initLogInterceptor())
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    // 日志级别
    private fun initLogInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    //在头部中做一些统一的处理
    private fun initHeaderInterceptor(): Interceptor {

        val interceptor = Interceptor {
            val request = it.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("charset", "UTF-8")
                .addHeader("token", AppPrefsUtils.getString(BaseConstant.KEY_SP_TOKEN))
                .build()

            it.proceed(request)


        }

        return interceptor

    }


    fun <T> create(service:Class<T>): T {
        return retrofit.create(service)
    }

    /**
     * 修改 BaseUrl
     * 因为不需要一直动态的切换 BaseUrl ，所以这里采用创建新的 retrofit 对象，来更改 baseUrl
     *
     * 如果需要不停的动态切换 BaseUrl，那么不再适合使用此方法（OKHttp 拦截器更合适）
     */
    fun changeBaseUrl(newBaseUrl: String) {
        //通过原来的 retrofit 的配置，构建一个新的 Builder，再重新设置 url
        retrofit = retrofit.newBuilder().baseUrl(newBaseUrl).build()

        Log.e("RetrofitFactory", "修改 BaseUrl: " + retrofit.baseUrl())
    }

    fun getCurrentBaseUrl(): String {
        return retrofit.baseUrl().toString()
    }

}