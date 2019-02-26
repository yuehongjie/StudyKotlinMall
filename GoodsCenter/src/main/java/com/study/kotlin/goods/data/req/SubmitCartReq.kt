package com.study.kotlin.goods.data.req

import com.study.kotlin.goods.data.protocol.CartGoods

/*
    提交购物车
 */
data class SubmitCartReq(val goodsList: List<CartGoods>, val totalPrice: Long)
