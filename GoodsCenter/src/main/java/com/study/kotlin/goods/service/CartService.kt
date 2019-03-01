package com.study.kotlin.goods.service

import com.study.kotlin.base.data.net.RetrofitFactory
import com.study.kotlin.base.ext.convert
import com.study.kotlin.goods.data.api.CartApi
import com.study.kotlin.goods.data.protocol.CartGoods
import com.study.kotlin.goods.data.req.AddCartReq
import rx.Observable

class CartService {

    /**
     * 添加购物车
     */
    fun addCart(goodsId: Int, goodsDesc: String, goodsIcon: String, goodsPrice: Long,
                goodsCount: Int, goodsSku: String): Observable<Int> {

        return RetrofitFactory.instance.create(CartApi::class.java)
            .addCart(AddCartReq(goodsId, goodsDesc, goodsIcon, goodsPrice, goodsCount, goodsSku))
            .convert()

    }

    fun getCartList(): Observable<MutableList<CartGoods>?> {

        return RetrofitFactory.instance.create(CartApi::class.java)
            .getCartList()
            .convert()

    }

}