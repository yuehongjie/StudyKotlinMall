package com.study.kotlin.goods.presenter

import com.study.kotlin.base.ext.execute
import com.study.kotlin.base.presenter.BasePresenter
import com.study.kotlin.base.rx.BaseSubscriber
import com.study.kotlin.goods.data.protocol.CartGoods
import com.study.kotlin.goods.presenter.view.CartListView
import com.study.kotlin.goods.service.CartService
import javax.inject.Inject

class CartListPresenter @Inject constructor() : BasePresenter<CartListView>() {

    @Inject
    lateinit var service: CartService

    fun getCartList() {

        if (!isNetWorkAvailable()) {
            return
        }

        mView.showLoading()

        service.getCartList()
            .execute(object: BaseSubscriber<MutableList<CartGoods>?>(mView) {

                override fun onNext(result: MutableList<CartGoods>?) {

                    mView.onGetCartListResult(result)

                }

            })

    }

}