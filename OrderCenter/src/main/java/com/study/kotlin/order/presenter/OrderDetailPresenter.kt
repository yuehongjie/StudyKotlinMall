package com.study.kotlin.order.presenter

import com.study.kotlin.base.ext.execute
import com.study.kotlin.base.presenter.BasePresenter
import com.study.kotlin.base.rx.BaseSubscriber
import com.study.kotlin.order.data.protocol.Order
import com.study.kotlin.order.presenter.view.OrderDetailView
import com.study.kotlin.order.service.OrderService
import javax.inject.Inject

class OrderDetailPresenter @Inject constructor(): BasePresenter<OrderDetailView>() {

    @Inject
    lateinit var service: OrderService

    fun getOrderById(orderId: Int) {
        if (!isNetWorkAvailable()) {
            return
        }

        mView.showLoading()

        service.getOrderById(orderId)
            .execute(object: BaseSubscriber<Order>(mView) {
                override fun onNext(result: Order) {
                    mView.onGetOrderByIdResult(result)
                }
            })

    }

}