package com.study.kotlin.message.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import cn.jpush.android.api.JPushInterface
import android.util.Log


class MessageReceiver: BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {

        val bundle = intent.extras

        when {

            JPushInterface.ACTION_REGISTRATION_ID == intent.action -> {
                logger("JPush 用户注册成功")
            }

            JPushInterface.ACTION_NOTIFICATION_RECEIVED == intent.action -> {
                logger("接受到推送下来的通知内容：${bundle?.getString(JPushInterface.EXTRA_ALERT)}")
            }

            JPushInterface.ACTION_MESSAGE_RECEIVED == intent.action -> {
                logger("接受到推送下来的自定义消息：${bundle?.getString(JPushInterface.EXTRA_MESSAGE)}")
            }

            JPushInterface.ACTION_NOTIFICATION_OPENED == intent.action -> {
                logger("用户点击打开了通知")

            }
            else -> {
                logger("Unhandled intent - " + intent.action)
            }
        }

    }

}

private fun logger(msg: String) {
    Log.e("MessageReceiver", msg)
}