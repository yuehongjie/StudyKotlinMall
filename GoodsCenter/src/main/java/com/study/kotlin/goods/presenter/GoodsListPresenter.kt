package com.study.kotlin.goods.presenter

import com.study.kotlin.base.ext.execute
import com.study.kotlin.base.presenter.BasePresenter
import com.study.kotlin.base.rx.BaseSubscriber
import com.study.kotlin.goods.data.protocol.Goods
import com.study.kotlin.goods.presenter.view.GoodsListView
import com.study.kotlin.goods.service.GoodsService
import javax.inject.Inject

class GoodsListPresenter @Inject constructor(): BasePresenter<GoodsListView>() {


    @Inject
    lateinit var goodsService: GoodsService

    /**
     * 通过分类获取商品列表
     */
    fun getGoodsList(categoryId: Int, pageNo: Int) {

        if (!isNetWorkAvailable()) {
            return
        }

        mView.showLoading()

        goodsService.getGoodsList(categoryId, pageNo)
            .execute(object: BaseSubscriber<MutableList<Goods>?>(mView) {

                override fun onNext(result: MutableList<Goods>?) {

                    mView.onGetGoodsListResult(result)
                }

            })

    }

    /**
     * 通过关键字获取商品列表
     */
    fun getGoodsListByKeyword(keyword: String, pageNo: Int) {

        if (!isNetWorkAvailable()) {
            return
        }

        mView.showLoading()
        goodsService.getGoodsListByKeyword(keyword, pageNo)
            .execute((object: BaseSubscriber<MutableList<Goods>?>(mView) {

                override fun onNext(result: MutableList<Goods>?) {

                    mView.onGetGoodsListResult(result)

                }
            }))

    }


}