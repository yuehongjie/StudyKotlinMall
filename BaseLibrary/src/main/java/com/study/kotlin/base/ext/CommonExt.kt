package com.study.kotlin.base.ext

import android.view.View
import com.study.kotlin.base.data.protocol.BaseResp
import com.study.kotlin.base.rx.BaseFunc
import com.study.kotlin.base.rx.BaseFuncBoolean
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


fun <T> Observable<BaseResp<T>>.convert():Observable<T> {

    return this.flatMap(BaseFunc())

}


fun <T> Observable<BaseResp<T>>.convertBoolean():Observable<Boolean> {

    return this.flatMap(BaseFuncBoolean())

}



//扩展 View 的点击事件 参数为 lambda 函数类型，高阶函数
fun View.onClick(method: () -> Unit) {

    this.setOnClickListener{
        method()
    }

}
