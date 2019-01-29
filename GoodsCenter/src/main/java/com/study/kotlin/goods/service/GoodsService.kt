package com.study.kotlin.goods.service

import com.study.kotlin.base.data.net.RetrofitFactory
import com.study.kotlin.base.ext.convert
import com.study.kotlin.goods.data.api.GoodsApi
import com.study.kotlin.goods.data.protocol.Goods
import com.study.kotlin.goods.data.req.GetGoodsDetailReq
import com.study.kotlin.goods.data.req.GetGoodsListByKeywordReq
import com.study.kotlin.goods.data.req.GetGoodsListReq
import rx.Observable

class GoodsService {

    fun getGoodsList(categoryId: Int, pageNo: Int) :Observable<MutableList<Goods>?> {

        return RetrofitFactory.instance.create(GoodsApi::class.java)
            .getGoodsList(GetGoodsListReq(categoryId, pageNo))
            .convert()

    }

    fun getGoodsListByKeyword(keyword: String, pageNo: Int): Observable<MutableList<Goods>?> {

        return RetrofitFactory.instance.create(GoodsApi::class.java)
            .getGoodsListByKeyword(GetGoodsListByKeywordReq(keyword, pageNo))
            .convert()

    }

    fun getGoodsDetail(goodsId: Int): Observable<Goods> {
        return RetrofitFactory.instance.create(GoodsApi::class.java)
            .getGoodsDetail(GetGoodsDetailReq(goodsId))
            .convert()
    }

}