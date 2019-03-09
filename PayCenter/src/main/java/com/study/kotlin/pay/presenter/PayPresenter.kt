package com.study.kotlin.pay.presenter

import com.study.kotlin.base.ext.execute
import com.study.kotlin.base.presenter.BasePresenter
import com.study.kotlin.base.rx.BaseSubscriber
import com.study.kotlin.pay.presenter.view.PayView
import com.study.kotlin.pay.service.PayService
import javax.inject.Inject

class PayPresenter @Inject constructor() : BasePresenter<PayView>() {


    @Inject
    lateinit var service: PayService

    fun getPaySign(orderId: Int, totalPrice: Long) {

        if (!isNetWorkAvailable()) {
            return
        }

        mView.showLoading()

        service.getPaySign(orderId, totalPrice)
            .execute(object: BaseSubscriber<String>(mView) {

                override fun onNext(result: String) {
                    mView.onGetPaySignResult(result)
                }

            })
    }

    fun payOrder(orderId: Int) {

        if (!isNetWorkAvailable()) {
            return
        }

        mView.showLoading()

        service.payOrder(orderId)
            .execute(object: BaseSubscriber<Boolean>(mView) {

                override fun onNext(result: Boolean) {
                    mView.onPayOrderResult(result)
                }
            })

    }

}