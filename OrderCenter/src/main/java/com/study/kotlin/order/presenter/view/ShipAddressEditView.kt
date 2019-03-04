package com.study.kotlin.order.presenter.view

import com.study.kotlin.base.presenter.view.BaseView

interface ShipAddressEditView: BaseView {

    /** 添加收货人地址 */
    fun onAddShipAddressResult(result: Boolean)

    /** 编辑收货人地址 */
    fun onEditShipAddressResult(result: Boolean)

}