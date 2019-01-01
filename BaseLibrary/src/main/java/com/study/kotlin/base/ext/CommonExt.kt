package com.study.kotlin.base.ext

import com.study.kotlin.base.rx.BaseSubscriber
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

//通用扩展

fun <T> Observable<T>.execute(subscriber : BaseSubscriber<T>): Subscription {

    return this.observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(subscriber)
}