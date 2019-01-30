package com.study.kotlin.base.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.study.kotlin.base.common.AppManager
import org.jetbrains.anko.find

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


    val rootContentView: View
    get() {
        val rootView = find<FrameLayout>(android.R.id.content)
        return rootView.getChildAt(0)
    }
}