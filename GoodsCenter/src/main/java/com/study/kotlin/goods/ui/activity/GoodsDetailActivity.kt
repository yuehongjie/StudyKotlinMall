package com.study.kotlin.goods.ui.activity

import android.os.Bundle
import android.view.Gravity
import android.view.View
import com.google.android.material.tabs.TabLayout
import com.study.kotlin.base.ext.onClick
import com.study.kotlin.base.ui.activity.BaseActivity
import com.study.kotlin.base.utils.AppPrefsUtils
import com.study.kotlin.goods.R
import com.study.kotlin.goods.data.common.GoodsConstant
import com.study.kotlin.goods.event.AddCartEvent
import com.study.kotlin.goods.event.UpdateCartSizeEvent
import com.study.kotlin.goods.ui.adapter.GoodsDetailVpAdapter
import com.study.kotlin.provider.common.afterLogin
import kotlinx.android.synthetic.main.activity_goods_detail.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.startActivity
import q.rorbin.badgeview.QBadgeView

class GoodsDetailActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mCartBadgeView: QBadgeView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_goods_detail)

        initView()

        EventBus.getDefault().register(this)

        loadData()

    }

    private fun initView() {

        mGoodsDetailTab.tabMode = TabLayout.MODE_FIXED
        mGoodsDetailVp.adapter = GoodsDetailVpAdapter(supportFragmentManager, this)
        mGoodsDetailTab.setupWithViewPager(mGoodsDetailVp)

        mAddCartBtn.onClick(this)
        mLeftIv.onClick { finish() }
        mEnterCartTv.onClick {
            startActivity<CartListActivity>()
        }

        mCartBadgeView = QBadgeView(this)

    }

    private fun loadData() {
        setCartCount()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: UpdateCartSizeEvent) {

        setCartCount()

    }

    private fun setCartCount() {

        mCartBadgeView.badgeGravity = Gravity.END or Gravity.TOP
        mCartBadgeView.setGravityOffset(20f, -2f, true)
        mCartBadgeView.setBadgeTextSize(10f, true)
        mCartBadgeView.bindTarget(mEnterCartTv)
        mCartBadgeView.badgeNumber = AppPrefsUtils.getInt(GoodsConstant.SP_CART_COUNT)

    }

    override fun onClick(v: View) {

        when(v.id) {

            R.id.mAddCartBtn -> {

                afterLogin {
                    //添加购物车的逻辑在 GoodsDetailTabOneFragment 中
                    EventBus.getDefault().post(AddCartEvent())
                }

            }

        }

    }


    override fun onDestroy() {
        super.onDestroy()

        EventBus.getDefault().unregister(this)
    }



}