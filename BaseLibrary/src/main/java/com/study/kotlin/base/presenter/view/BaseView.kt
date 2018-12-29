package com.study.kotlin.base.presenter.view

/**
 * 只做回调用，不持有任何引用
 */
interface BaseView {

    fun showLoading()
    fun hideLoading()
    fun onError()

}