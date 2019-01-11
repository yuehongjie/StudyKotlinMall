package com.study.kotlin.base.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.study.kotlin.base.common.AppManager

/**
 * 所有 Activity 的基类
 * */
open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppManager.instance.addActivity(this)
    }


    override fun onDestroy() {
        super.onDestroy()

        AppManager.instance.finishActivity(this)
    }
}