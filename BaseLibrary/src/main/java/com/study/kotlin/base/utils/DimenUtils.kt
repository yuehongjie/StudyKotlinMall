package com.study.kotlin.base.utils

import android.content.Context

object DimenUtils {


    fun dp2px(context: Context, dp: Int): Int {
        return (context.resources.displayMetrics.density * dp).toInt()
    }

    fun px2dp(context: Context, px: Int): Int {
        return (px / context.resources.displayMetrics.density).toInt()
    }

}