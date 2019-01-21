package com.kotlin.provider.common

import com.study.kotlin.base.common.BaseConstant

/*
    业务常量
 */
class ProviderConstant {
    companion object {
        //用户名称
        const val KEY_SP_USER_NAME = "sp_user_name"
        //用户电话
        const val KEY_SP_USER_MOBILE = "sp_user_mobile"
        //用户头像
        const val KEY_SP_USER_ICON = "sp_user_icon"
        //用户性别
        const val KEY_SP_USER_GENDER = "sp_user_gender"
        //用户签名
        const val KEY_SP_USER_SIGN = "sp_user_sign"
        //用户 token 即 id
        const val KEY_SP_TOKEN = BaseConstant.KEY_SP_TOKEN

        //订单ID
        const val KEY_ORDER_ID = "order_id"
        //订单价格
        const val KEY_ORDER_PRICE = "order_price"
    }


}
