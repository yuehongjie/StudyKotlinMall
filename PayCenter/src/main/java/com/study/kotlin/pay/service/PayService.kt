package com.study.kotlin.pay.service

import com.study.kotlin.base.data.net.RetrofitFactory
import com.study.kotlin.base.ext.convert
import com.study.kotlin.base.ext.convertBoolean
import com.study.kotlin.pay.data.api.PayApi
import com.study.kotlin.pay.data.req.GetPaySignReq
import com.study.kotlin.pay.data.req.PayOrderReq
import rx.Observable


class PayService {

    /**
     * 生成预支付订单
     */
    fun getPaySign(orderId: Int, totalPrice: Long): Observable<String> {

        return RetrofitFactory.instance.create(PayApi::class.java)
            .getPaySign(GetPaySignReq(orderId, totalPrice))
            .convert()

    }

    /**
     * 刷新订单状态
     */
    fun payOrder(orderId: Int): Observable<Boolean> {

        return RetrofitFactory.instance.create(PayApi::class.java)
            .payOrder(PayOrderReq(orderId))
            .convertBoolean()

    }

}