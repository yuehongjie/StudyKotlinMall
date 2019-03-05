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

}