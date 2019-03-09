package com.study.kotlin.pay.presenter.view

import com.study.kotlin.base.presenter.view.BaseView

interface PayView: BaseView {

    fun onGetPaySignResult(result: String)

    fun onPayOrderResult(result: Boolean)

}