package com.study.kotlin.message.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import cn.jpush.android.api.JPushInterface
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.study.kotlin.message.event.MessageBadgeEvent
import com.study.kotlin.provider.common.ProviderConstant
import com.study.kotlin.provider.router.RouterPath
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject


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

                //用户登录成功会服务器会发送自定义消息，发送事件，显示消息红点
                EventBus.getDefault().post(MessageBadgeEvent(true))

            }

            JPushInterface.ACTION_NOTIFICATION_OPENED == intent.action -> {
                logger("用户点击打开了通知")

                //获取到订单通知（json）
                val orderExtra = bundle?.getString(JPushInterface.EXTRA_EXTRA)
                //解析 json 拿到订单
                val json = JSONObject(orderExtra)
                val orderId = json.getInt("orderId")
                //通过 ARouter 跳转到订单详情页
                ARouter.getInstance()
                    .build(RouterPath.MessageCenter.PATH_MESSAGE_ORDER)
                    .withInt(ProviderConstant.KEY_ORDER_ID, orderId)
                    .navigation()

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