package com.study.kotlin.goods.ui.activity

import android.os.Bundle
import android.util.Log
import com.google.android.material.tabs.TabLayout
import com.study.kotlin.base.ui.activity.BaseActivity
import com.study.kotlin.goods.R
import com.study.kotlin.goods.event.TestEvent
import com.study.kotlin.goods.ui.adapter.GoodsDetailVpAdapter
import kotlinx.android.synthetic.main.activity_goods_detail.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class GoodsDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_goods_detail)

        initView()

        EventBus.getDefault().register(this)

    }

    private fun initView() {

        mGoodsDetailTab.tabMode = TabLayout.MODE_FIXED
        mGoodsDetailVp.adapter = GoodsDetailVpAdapter(supportFragmentManager, this)
        mGoodsDetailTab.setupWithViewPager(mGoodsDetailVp)

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: TestEvent) {

        Log.e("GoodsDetailActivity", "收到测试事件")
    }


    override fun onDestroy() {
        super.onDestroy()

        EventBus.getDefault().unregister(this)
    }



}