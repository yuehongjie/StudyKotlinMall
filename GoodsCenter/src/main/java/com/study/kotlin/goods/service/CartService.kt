package com.study.kotlin.goods.service

import com.study.kotlin.base.data.net.RetrofitFactory
import com.study.kotlin.base.ext.convert
import com.study.kotlin.base.ext.convertBoolean
import com.study.kotlin.goods.data.api.CartApi
import com.study.kotlin.goods.data.protocol.CartGoods
import com.study.kotlin.goods.data.req.AddCartReq
import com.study.kotlin.goods.data.req.DeleteCartReq
import com.study.kotlin.goods.data.req.SubmitCartReq
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

    //获取购物车列表
    fun getCartList(): Observable<MutableList<CartGoods>?> {

        return RetrofitFactory.instance.create(CartApi::class.java)
            .getCartList()
            .convert()

    }

    //删除购物车商品列表
    fun deleteCartList(cartIdList: List<Int>): Observable<Boolean> {

        return RetrofitFactory.instance.create(CartApi::class.java)
            .deleteCartList(DeleteCartReq(cartIdList))
            .convertBoolean()

    }

    //提交订单
    fun submitCart(goodsList: List<CartGoods>, totalPrice: Long): Observable<Int> {

        return RetrofitFactory.instance.create(CartApi::class.java)
            .submitCart(SubmitCartReq(goodsList, totalPrice))
            .convert()

    }

}