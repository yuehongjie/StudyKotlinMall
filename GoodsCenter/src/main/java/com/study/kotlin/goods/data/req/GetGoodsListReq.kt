package com.study.kotlin.goods.data.req

/*
    按分类搜索商品
 */
data class GetGoodsListReq(val categoryId: Int,val pageNo: Int)
