package com.study.kotlin.order.service

import com.study.kotlin.base.data.net.RetrofitFactory
import com.study.kotlin.base.ext.convert
import com.study.kotlin.base.ext.convertBoolean
import com.study.kotlin.order.data.api.OrderApi
import com.study.kotlin.order.data.protocol.Order
import com.study.kotlin.order.data.req.GetOrderByIdReq
import com.study.kotlin.order.data.req.SubmitOrderReq
import rx.Observable

class OrderService {

    fun getOrderById(orderId: Int): Observable<Order>{

        return RetrofitFactory.instance.create(OrderApi::class.java)
            .getOrderById(GetOrderByIdReq(orderId))
            .convert()

    }

    fun submitOrder(order: Order): Observable<Boolean> {

        return RetrofitFactory.instance.create(OrderApi::class.java)
            .submitOrder(SubmitOrderReq(order))
            .convertBoolean()
    }

}