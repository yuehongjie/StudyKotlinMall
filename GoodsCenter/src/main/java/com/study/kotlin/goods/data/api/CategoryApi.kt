package com.study.kotlin.goods.data.api

import com.study.kotlin.base.data.protocol.BaseResp
import com.study.kotlin.goods.data.protocol.Category
import com.study.kotlin.goods.data.req.GetCategoryReq
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

/**
 * 分类接口
 */
interface CategoryApi {

    @POST("category/getCategory")
    fun getCategory(@Body req: GetCategoryReq): Observable<BaseResp<MutableList<Category>?>>

}