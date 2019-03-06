package com.study.kotlin.order.presenter

import com.study.kotlin.base.ext.execute
import com.study.kotlin.base.presenter.BasePresenter
import com.study.kotlin.base.rx.BaseSubscriber
import com.study.kotlin.order.data.protocol.Order
import com.study.kotlin.order.presenter.view.OrderListView
import com.study.kotlin.order.service.OrderService
import javax.inject.Inject

class OrderListPresenter @Inject constructor(): BasePresenter<OrderListView>() {

    @Inject
    lateinit var service: OrderService

    /**
     * 获取订单列表
     */
    fun getOrderList(orderStatus: Int) {

        if (!isNetWorkAvailable()) {
            return
        }

        mView.showLoading()

        service.getOrderList(orderStatus)
            .execute(object: BaseSubscriber<MutableList<Order>?>(mView) {
                override fun onNext(result: MutableList<Order>?) {

                    mView.onGetOrderListResult(result)

                }
            })
    }

    /**
     * 确认收货
     */
    fun confirmOrder(orderId: Int) {

        if (!isNetWorkAvailable()) {
            return
        }

        mView.showLoading()

        service.confirmOrder(orderId)
            .execute(object: BaseSubscriber<Boolean>(mView) {
                override fun onNext(result: Boolean) {
                    mView.onConfirmOrderResult(result)
                }
            })

    }

    /**
     * 取消订单
     */
    fun cancelOrder(orderId: Int) {

        if (!isNetWorkAvailable()) {
            return
        }

        mView.showLoading()

        service.cancelOrder(orderId)
            .execute(object: BaseSubscriber<Boolean>(mView) {
                override fun onNext(result: Boolean) {
                    mView.onCancelOrderResult(result)
                }
            })
    }

}