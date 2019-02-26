package com.study.kotlin.goods.presenter

import com.study.kotlin.base.ext.execute
import com.study.kotlin.base.presenter.BasePresenter
import com.study.kotlin.base.rx.BaseSubscriber
import com.study.kotlin.goods.data.protocol.Goods
import com.study.kotlin.goods.presenter.view.GoodsDetailView
import com.study.kotlin.goods.service.CartService
import com.study.kotlin.goods.service.GoodsService
import javax.inject.Inject

class GoodsDetailPresenter @Inject constructor(): BasePresenter<GoodsDetailView>() {


    @Inject
    lateinit var goodsService: GoodsService

    @Inject
    lateinit var cartService: CartService


    /**
     * 获取商品详情
     */
    fun getGoodsDetail(goodsId: Int) {

        if (!isNetWorkAvailable()) {
            return
        }

        mView.showLoading()

        goodsService.getGoodsDetail(goodsId)
            .execute(object: BaseSubscriber<Goods>(mView) {
                override fun onNext(result: Goods) {

                    mView.onGetGoodsDetailResult(result)

                }
            })

    }

    fun addCart(goodsId: Int, goodsDesc: String, goodsIcon: String, goodsPrice: Long,
                    goodsCount: Int, goodsSku: String) {

        if (!isNetWorkAvailable()) {
            return
        }

        mView.showLoading()

        cartService.addCart(goodsId, goodsDesc, goodsIcon, goodsPrice, goodsCount, goodsSku)
            .execute(object: BaseSubscriber<Int>(mView) {

                override fun onNext(result: Int) {

                    mView.onAddCartResult(result)

                }

            })



    }

}