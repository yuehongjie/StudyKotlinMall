package com.study.kotlin.order.presenter.view

import com.study.kotlin.base.presenter.view.BaseView
import com.study.kotlin.order.data.protocol.ShipAddress

/**
 * 地址管理
 */
interface ShipAddressView:BaseView {

    fun onGetShipAddressListResult(result: MutableList<ShipAddress>?)

    fun onEditShipAddressResult(result: Boolean)

    fun onDeleteShipAddressResult(result: Boolean)
}