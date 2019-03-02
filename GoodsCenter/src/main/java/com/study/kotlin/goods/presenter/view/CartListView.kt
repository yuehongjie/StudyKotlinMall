package com.study.kotlin.goods.presenter.view

import com.study.kotlin.base.presenter.view.BaseView
import com.study.kotlin.goods.data.protocol.CartGoods

interface CartListView: BaseView {

    fun onGetCartListResult(result: MutableList<CartGoods>?)

    fun onDeleteCartListResult(result: Boolean)

    fun onSubmitCartResult(result: Int)

}