package com.study.kotlin.base.ext

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.study.kotlin.base.data.protocol.BaseResp
import com.study.kotlin.base.rx.BaseFunc
import com.study.kotlin.base.rx.BaseFuncBoolean
import com.study.kotlin.base.rx.BaseSubscriber
import com.study.kotlin.base.utils.GlideUtils
import com.study.kotlin.base.widgets.DefaultTextWatcher
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

fun View.onClick(listener: View.OnClickListener) {

    this.setOnClickListener(listener)

}

/**
 * 根据输入框的文本内容变化，确定按钮是否可用，具体的判断方式由传入的 lambda 函数判断
 */
fun Button.enable(editText: EditText, method: () -> Boolean) {

    val btn = this

    editText.addTextChangedListener(object: DefaultTextWatcher() {

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            super.onTextChanged(s, start, before, count)

            btn.isEnabled = method()

        }

    } )

}


/**
 * ImageView 加载图片
 */
fun ImageView.loadUrl(url: String) {

    GlideUtils.loadImage(context, url, this)

}

/**
 * 扩展 View 是否可见
 */
fun View.setVisible(visible: Boolean) {

    this.visibility = if (visible) View.VISIBLE else View.GONE

}