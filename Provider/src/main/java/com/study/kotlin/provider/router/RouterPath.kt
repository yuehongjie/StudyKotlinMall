package com.study.kotlin.provider.router

//模块路由路径
object RouterPath {

    //用户中心
    class UserCenter{
        companion object {
            /** 登录 */
            const val PATH_LOGIN = "/userCenter/login"  // 路径至少要有两级 且以 / 开始 否则报错：
            // ARouter::Extract the default group failed, the path must be start with '/' and contain more than 2 '/'!
        }
    }

    //订单中心
    class OrderCenter{
        companion object {
            /** 订单确认页 */
            const val PATH_ORDER_CONFIRM = "/orderCenter/orderConfirm"
        }
    }

    //支付中心
    class PayCenter{
        companion object {
            /**
             * 支付页面
             */
            const val PATH_PAY = "/payCenter/pay"
        }
    }

    //消息中心
    class MessageCenter{
        companion object {
            /**
             * push id
             */
            const val PATH_MESSAGE_PUSH = "/messageCenter/push"
            const val PATH_MESSAGE_ORDER = "/messageCenter/order" //点击通知打开订单详情页
        }
    }

}