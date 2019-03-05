package com.study.kotlin.order.presenter.view

import com.study.kotlin.base.presenter.view.BaseView
import com.study.kotlin.order.data.protocol.Order

interface OrderListView:BaseView {

    fun onGetOrderListResult(result: MutableList<Order>?)
}