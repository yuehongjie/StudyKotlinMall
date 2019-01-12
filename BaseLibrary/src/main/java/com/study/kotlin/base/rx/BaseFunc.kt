package com.study.kotlin.base.rx

import com.study.kotlin.base.common.ResultCode
import com.study.kotlin.base.data.protocol.BaseResp
import rx.Observable
import rx.functions.Func1

/**
 * 定义一个 返回 Boolean 的 Func1
 */
class BaseFunc<T>: Func1< BaseResp<T>, Observable<T> > {

    override fun call(t: BaseResp<T>): Observable<T> {

        if (t.status != ResultCode.SUCCESS) {
            return Observable.error(BaseException(t.status, t.message))
        }

        return Observable.just(t.data)

    }
}
