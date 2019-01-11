package com.study.kotlin.base.rx

import com.study.kotlin.base.data.protocol.BaseResp
import rx.Observable
import rx.functions.Func1

/**
 * 定义一个 返回 Boolean 的 Func1
 */
class BaseFuncBoolean<T>: Func1<BaseResp<T>, Observable<Boolean>> {

    override fun call(t: BaseResp<T>): Observable<Boolean> {

        if (t.status != 0) {
            // 会自动进行类型转换 Observable.error<Boolean>(BaseException(t.status, t.message))
            return Observable.error(BaseException(t.status, t.message))
        }

        return Observable.just(true)

    }
}
