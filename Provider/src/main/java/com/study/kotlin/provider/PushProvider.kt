package com.study.kotlin.provider

import com.alibaba.android.arouter.facade.template.IProvider

/**
 * 跨模块的接口调用：
 * 在用户模块做登录的时候获取到消息模块中的 JPushId（当前 app 当前手机上，自动注册的极光 id）
 *
 * 1. 声明接口，暴露服务
 * 2. 在消息模块实现该接口中的方法
 * 3. 在登录的时候通过依赖注入解耦，发现服务（创建服务的实例）
 */
interface PushProvider: IProvider {

    fun getPushId():String

}