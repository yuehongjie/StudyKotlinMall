package com.study.kotlin.order.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.kotlin.base.utils.YuanFenConverter
import com.study.kotlin.base.ext.onClick
import com.study.kotlin.base.ext.setVisible
import com.study.kotlin.base.ui.activity.BaseMvpActivity
import com.study.kotlin.order.R
import com.study.kotlin.order.data.common.OrderConstant
import com.study.kotlin.order.data.protocol.Order
import com.study.kotlin.order.event.SelectAddressEvent
import com.study.kotlin.order.injection.component.DaggerOrderComponent
import com.study.kotlin.order.presenter.OrderConfirmPresenter
import com.study.kotlin.order.presenter.view.OrderConfirmView
import com.study.kotlin.order.ui.adapter.OrderGoodsAdapter
import com.study.kotlin.provider.common.ProviderConstant
import com.study.kotlin.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_order_confirm.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

@Route(path = RouterPath.OrderCenter.PATH_ORDER_CONFIRM)
class OrderConfirmActivity : BaseMvpActivity<OrderConfirmPresenter>(), OrderConfirmView {

    @Autowired(name = ProviderConstant.KEY_ORDER_ID) //使用 ARouter 注解获取传入的参数
    @JvmField
    var mOrderId: Int = 0

    private lateinit var mAdapter: OrderGoodsAdapter

    private lateinit var mCurrentOrder: Order

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirm)

        ARouter.getInstance().inject(this)

        initView()
        loadData()

        EventBus.getDefault().register(this)
    }

    override fun injectComponent() {
        DaggerOrderComponent.builder()
            .activityComponent(activityComponent)
            .build()
            .inject(this)

        mPresenter.mView = this
    }

    private fun initView() {

        mSelectShipTv.onClick {
            startActivity<ShipAddressActivity>(OrderConstant.KEY_IS_SELECT_ADDRESS to true)
        }

        mShipView.onClick {
            startActivity<ShipAddressActivity>(OrderConstant.KEY_IS_SELECT_ADDRESS to true)
        }

        mSubmitOrderBtn.onClick {
            mPresenter.submitOrder(mCurrentOrder)
        }

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

        mTotalPriceTv.text = "合计：${YuanFenConverter.changeF2YWithUnit(result.totalPrice)}"
        mAdapter.addData(result.orderGoodsList)

        updateAddressView()
    }


    //选择联系人事件
    @Subscribe
    fun onSelectAddressEvent(event: SelectAddressEvent) {
        mCurrentOrder.shipAddress = event.address
        updateAddressView()
    }


    /**
     * 是选择联系人 还是显示联系人
     */
    private fun updateAddressView() {
        if (mCurrentOrder.shipAddress != null) {

            mShipView.setVisible(true)
            mSelectShipTv.setVisible(false)
            val name = "${mCurrentOrder.shipAddress?.shipUserName}   ${mCurrentOrder.shipAddress?.shipUserMobile} "
            mShipNameTv.text = name
            mShipAddressTv.text = "${mCurrentOrder.shipAddress?.shipAddress}"

        }else {
            mShipView.setVisible(false)
            mSelectShipTv.setVisible(true)
        }
    }

    /** 提交订单成功 */
    override fun onSubmitOrderResult(result: Boolean) {
        toast("提交订单成功")
        ARouter.getInstance()
            .build(RouterPath.PayCenter.PATH_PAY)
            .withInt(ProviderConstant.KEY_ORDER_ID, mCurrentOrder.id)
            .withLong(ProviderConstant.KEY_ORDER_PRICE, mCurrentOrder.totalPrice)
            .navigation()

        finish()
    }


    override fun onDestroy() {
        super.onDestroy()

        EventBus.getDefault().unregister(this)
    }

}
