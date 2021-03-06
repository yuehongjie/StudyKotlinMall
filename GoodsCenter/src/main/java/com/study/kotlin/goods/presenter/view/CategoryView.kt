package com.study.kotlin.goods.presenter.view

import com.study.kotlin.base.presenter.view.BaseView
import com.study.kotlin.goods.data.protocol.Category

interface CategoryView: BaseView{

    fun onGetCategoryResult(result: MutableList<Category>?)

}