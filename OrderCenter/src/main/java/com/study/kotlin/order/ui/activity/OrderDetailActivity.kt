package com.study.kotlin.order.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.kotlin.base.utils.YuanFenConverter
import com.study.kotlin.base.ui.activity.BaseMvpActivity
import com.study.kotlin.order.R
import com.study.kotlin.order.data.protocol.Order
import com.study.kotlin.order.injection.component.DaggerOrderComponent
import com.study.kotlin.order.presenter.OrderDetailPresenter
import com.study.kotlin.order.presenter.view.OrderDetailView
import com.study.kotlin.order.ui.adapter.OrderGoodsAdapter
import com.study.kotlin.provider.common.ProviderConstant
import com.study.kotlin.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_order_detail.*


@Route(path = RouterPath.MessageCenter.PATH_MESSAGE_ORDER) //点击通过也可以打开通知详情页
class OrderDetailActivity : BaseMvpActivity<OrderDetailPresenter>(), OrderDetailView {

    lateinit var mAdapter: OrderGoodsAdapter

    private var mOrderId: Int = 0

    private lateinit var mCurrentOrder: Order

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        mOrderId = intent.getIntExtra(ProviderConstant.KEY_ORDER_ID, 0)

        initView()
        loadData()
    }

    override fun injectComponent() {
        DaggerOrderComponent.builder()
            .activityComponent(activityComponent)
            .build()
            .inject(this)

        mPresenter.mView = this
    }

    private fun initView() {
        mOrderGoodsRv.layoutManager = LinearLayoutManager(this)
        mAdapter = OrderGoodsAdapter(this)
        mOrderGoodsRv.adapter = mAdapter

    }

    /** 加载订单 */
    private fun loadData(){
        mPresenter.getOrderById(mOrderId)
    }

    /** 获取订单详情 */
    override fun onGetOrderByIdResult(result: Order) {
        mCurrentOrder = result
        mShipNameTv.setContentText(result.shipAddress!!.shipUserName)
        mShipMobileTv.setContentText(result.shipAddress!!.shipUserMobile)
        mShipAddressTv.setContentText(result.shipAddress!!.shipAddress)
        mTotalPriceTv.setContentText(YuanFenConverter.changeF2YWithUnit(result.totalPrice))

        mAdapter.setData(result.orderGoodsList)

    }

}
