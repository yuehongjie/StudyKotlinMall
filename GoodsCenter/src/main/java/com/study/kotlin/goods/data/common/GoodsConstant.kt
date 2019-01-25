package com.study.kotlin.goods.data.common

object GoodsConstant {

    //商品分类 id
    const val KEY_CATEGORY_ID = "categoryId"

    //商品搜索的关键字
    const val KEY_GOODS_KEYWORD = "goods_keyword"

    //搜索历史 本地存储
    const val SP_SEARCH_HISTORY = "search_history"


    //商品列表页按哪种方式获取商品列表：关键字搜索、分类搜索
    const val KEY_GOODS_LIST_TYPE = "goods_list_type"

    //商品列表搜索方式为关键字搜索
    const val KEY_TYPE_SEARCH_KEYWORD = 1
    //商品列表搜索方式为分类搜索
    const val KEY_TYPE_SEARCH_CATEGORY = 2


}