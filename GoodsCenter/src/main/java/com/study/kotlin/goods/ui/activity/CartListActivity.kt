package com.study.kotlin.goods.ui.activity

import android.os.Bundle
import com.study.kotlin.base.ui.activity.BaseActivity
import com.study.kotlin.goods.R
import com.study.kotlin.goods.ui.fragment.CartListFragment

class CartListActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_cart)

        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_cart)
        (fragment as CartListFragment).setBackVisible(true)
    }

}