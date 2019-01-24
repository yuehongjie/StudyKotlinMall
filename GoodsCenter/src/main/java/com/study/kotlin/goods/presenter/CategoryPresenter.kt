package com.study.kotlin.goods.presenter

import com.study.kotlin.base.ext.execute
import com.study.kotlin.base.presenter.BasePresenter
import com.study.kotlin.base.rx.BaseSubscriber
import com.study.kotlin.goods.data.protocol.Category
import com.study.kotlin.goods.presenter.view.CategoryView
import com.study.kotlin.goods.service.CategoryService
import javax.inject.Inject

class CategoryPresenter @Inject constructor(): BasePresenter<CategoryView>() {

    @Inject
    lateinit var categoryService: CategoryService

    fun getCategory(parentId: Int) {

        if (!isNetWorkAvailable()) {
            return
        }

        mView.showLoading()
        categoryService.getCategory(parentId)
            .execute(object: BaseSubscriber<MutableList<Category>?>(mView) {

                override fun onNext(result: MutableList<Category>?) {

                    mView.onGetCategoryResult(result)

                }

            })
    }

}