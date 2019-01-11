package com.study.kotlin.base.presenter

import android.content.Context
import com.study.kotlin.base.utils.NetWorkUtils
import com.study.kotlin.base.presenter.view.BaseView
import javax.inject.Inject

/**
 * MVP  base presenter 持有 BaseView 的引用
 *
 * mView 泛型:上限是 BaseView，实际类型根据实际值定
 */
open class BasePresenter <T: BaseView> {

    lateinit var mView: T

    @Inject
    lateinit var mContext: Context

    fun isNetWorkAvailable():Boolean {

        return NetWorkUtils.isNetWorkAvailable(mContext)

    }

}