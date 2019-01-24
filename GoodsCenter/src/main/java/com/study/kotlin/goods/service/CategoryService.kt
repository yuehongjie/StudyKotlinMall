package com.study.kotlin.goods.service

import com.study.kotlin.base.data.net.RetrofitFactory
import com.study.kotlin.base.ext.convert
import com.study.kotlin.goods.data.api.CategoryApi
import com.study.kotlin.goods.data.protocol.Category
import com.study.kotlin.goods.data.req.GetCategoryReq
import rx.Observable

class CategoryService {

    fun getCategory(parentId: Int):Observable<MutableList<Category>?> {

        return RetrofitFactory.instance.create(CategoryApi::class.java)
            .getCategory(GetCategoryReq(parentId))
            .convert()

    }

}