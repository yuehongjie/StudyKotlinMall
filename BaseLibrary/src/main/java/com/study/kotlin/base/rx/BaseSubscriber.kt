package com.study.kotlin.base.rx

import com.study.kotlin.base.presenter.view.BaseView
import rx.Subscriber

open  class BaseSubscriber<T>(private val mBaseView: BaseView): Subscriber<T>() {


    override fun onNext(t: T) {

    }

    //数据加载成功会调用
    override fun onCompleted() {
        mBaseView.hideLoading()
    }

    //数据加载失败会调用
    override fun onError(e: Throwable?) {
        mBaseView.hideLoading()
    }
}