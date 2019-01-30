package com.study.kotlin.base.ui.fragment

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {

    lateinit var mActivity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mActivity = activity as Activity

    }
}