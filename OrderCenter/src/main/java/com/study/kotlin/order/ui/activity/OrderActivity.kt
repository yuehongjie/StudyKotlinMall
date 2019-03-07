package com.study.kotlin.order.ui.activity

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import com.study.kotlin.base.ui.activity.BaseActivity
import com.study.kotlin.order.R
import com.study.kotlin.order.data.common.OrderConstant
import com.study.kotlin.order.data.common.OrderStatus
import com.study.kotlin.order.ui.adapter.OrderVpAdapter
import kotlinx.android.synthetic.main.activity_order.*

class OrderActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        initView()
    }

    private fun initView() {

        mOrderTab.tabMode = TabLayout.MODE_FIXED
        mOrderVp.adapter = OrderVpAdapter(supportFragmentManager)
        mOrderTab.setupWithViewPager(mOrderVp)
        mOrderVp.offscreenPageLimit = 4 //缓存几个页面

        mOrderVp.currentItem = intent.getIntExtra(OrderConstant.KEY_ORDER_STATUS, OrderStatus.ORDER_ALL)

    }
}
