package com.study.kotlin.goods.presenter

import com.study.kotlin.base.ext.execute
import com.study.kotlin.base.presenter.BasePresenter
import com.study.kotlin.base.rx.BaseSubscriber
import com.study.kotlin.goods.data.protocol.Goods
import com.study.kotlin.goods.presenter.view.GoodsDetailView
import com.study.kotlin.goods.service.GoodsService
import javax.inject.Inject

class GoodsDetailPresenter @Inject constructor(): BasePresenter<GoodsDetailView>() {


    @Inject
    lateinit var goodsService: GoodsService


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

}