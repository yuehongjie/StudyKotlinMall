package com.study.kotlin.order.service

import com.study.kotlin.base.data.net.RetrofitFactory
import com.study.kotlin.base.ext.convert
import com.study.kotlin.base.ext.convertBoolean
import com.study.kotlin.order.data.api.OrderApi
import com.study.kotlin.order.data.protocol.Order
import com.study.kotlin.order.data.req.GetOrderByIdReq
import com.study.kotlin.order.data.req.GetOrderListReq
import com.study.kotlin.order.data.req.SubmitOrderReq
import rx.Observable

class OrderService {

    //根据订单 id 获取订单
    fun getOrderById(orderId: Int): Observable<Order>{

        return RetrofitFactory.instance.create(OrderApi::class.java)
            .getOrderById(GetOrderByIdReq(orderId))
            .convert()

    }

    //提交订单
    fun submitOrder(order: Order): Observable<Boolean> {

        return RetrofitFactory.instance.create(OrderApi::class.java)
            .submitOrder(SubmitOrderReq(order))
            .convertBoolean()
    }

    //获取订单列表
    fun getOrderList(orderStatus: Int):Observable<MutableList<Order>?> {

        return RetrofitFactory.instance.create(OrderApi::class.java)
            .getOrderList(GetOrderListReq(orderStatus))
            .convert()

    }

}