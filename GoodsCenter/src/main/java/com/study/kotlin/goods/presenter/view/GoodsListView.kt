package com.study.kotlin.goods.presenter.view

import com.study.kotlin.base.presenter.view.BaseView
import com.study.kotlin.goods.data.protocol.Goods

interface GoodsListView: BaseView {

    fun onGetGoodsListResult(result: MutableList<Goods>?)

}