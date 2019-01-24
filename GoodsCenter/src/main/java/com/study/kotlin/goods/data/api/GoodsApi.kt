package com.study.kotlin.goods.data.api

import com.study.kotlin.base.data.protocol.BaseResp
import com.study.kotlin.goods.data.protocol.Goods
import com.study.kotlin.goods.data.req.GetGoodsDetailReq
import com.study.kotlin.goods.data.req.GetGoodsListByKeywordReq
import com.study.kotlin.goods.data.req.GetGoodsListReq
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

/*
    商品接口
 */
interface GoodsApi {
    /*
        获取商品列表
     */
    @POST("goods/getGoodsList")
    fun getGoodsList(@Body req: GetGoodsListReq): Observable<BaseResp<MutableList<Goods>?>>

    /*
        获取商品列表
     */
    @POST("goods/getGoodsListByKeyword")
    fun getGoodsListByKeyword(@Body req: GetGoodsListByKeywordReq): Observable<BaseResp<MutableList<Goods>?>>

    /*
        获取商品详情
     */
    @POST("goods/getGoodsDetail")
    fun getGoodsDetail(@Body req: GetGoodsDetailReq): Observable<BaseResp<Goods>>
}
