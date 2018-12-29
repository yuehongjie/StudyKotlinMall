package com.study.kotlin.base.ui.activity

import com.study.kotlin.base.presenter.BasePresenter
import com.study.kotlin.base.presenter.view.BaseView

/**
 * 所有 MVP 架构的 Activity 的基类
 *
 * 持有 Presenter 的引用， mPresenter 是泛型，上限是 BasePresenter，实际类型由子类的类决定
 *
 * 同时实现 BaseView 接口
 *
 */
open class BaseMvpActivity<T: BasePresenter<*>> : BaseActivity(), BaseView {


    lateinit var mPresenter: T


    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onError() {

    }


}