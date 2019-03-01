package com.study.kotlin.goods.widgets

import android.content.Context
import android.util.AttributeSet
import ren.qinc.numberbutton.NumberButton

/**
 * 增加变化的监听
 */
class MyNumberButton(context: Context?, attrs: AttributeSet? = null) : NumberButton(context, attrs) {

    private var mListener: OnNumberChangeListener? = null

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        super.onTextChanged(s, start, before, count)

        mListener?.onNumberChange(number)
    }

    fun setOnNumberChangeListener(listener: OnNumberChangeListener) {
        this.mListener = listener
    }

    interface OnNumberChangeListener {

        fun onNumberChange(number : Int)

    }

}