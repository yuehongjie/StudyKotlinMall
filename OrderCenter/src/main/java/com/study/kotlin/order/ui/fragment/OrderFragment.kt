package com.study.kotlin.order.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.kennyc.view.MultiStateView
import com.study.kotlin.base.adapter.BaseRecyclerViewAdapter
import com.study.kotlin.base.ui.fragment.BaseMvpFragment
import com.study.kotlin.order.R
import com.study.kotlin.order.data.common.OrderConstant
import com.study.kotlin.order.data.common.OrderStatus
import com.study.kotlin.order.data.protocol.Order
import com.study.kotlin.order.event.CancelOrderEvent
import com.study.kotlin.order.event.ConfirmOrderEvent
import com.study.kotlin.order.injection.component.DaggerOrderComponent
import com.study.kotlin.order.presenter.OrderListPresenter
import com.study.kotlin.order.presenter.view.OrderListView
import com.study.kotlin.order.ui.activity.OrderDetailActivity
import com.study.kotlin.order.ui.adapter.OnOptClickListener
import com.study.kotlin.order.ui.adapter.OrderAdapter
import com.study.kotlin.provider.common.ProviderConstant
import com.study.kotlin.provider.event.PaySuccessEvent
import com.study.kotlin.provider.router.RouterPath
import kotlinx.android.synthetic.main.fragment_order.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class OrderFragment : BaseMvpFragment<OrderListPresenter>(), OrderListView {

    private var mOrderStatus: Int = 0
    private lateinit var mAdapter: OrderAdapter

    companion object {
        /**
         * 伴生对象提供静态方法创建 Fragment
         */
        @JvmStatic
        fun newInstance(orderStatus: Int) =
            OrderFragment().apply {
                arguments = Bundle().apply {
                    putInt(OrderConstant.KEY_ORDER_STATUS, orderStatus)
                }
            }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            mOrderStatus = it.getInt(OrderConstant.KEY_ORDER_STATUS)
        }
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
        mOrderRv.layoutManager = LinearLayoutManager(mActivity)
        mAdapter = OrderAdapter(mActivity)
        mOrderRv.adapter = mAdapter

        mAdapter.listener = object: OnOptClickListener{
            override fun onOptClick(optType: Int, order: Order) {
                doOpt(optType, order)
            }
        }

        mAdapter.setOnItemClickListener(object: BaseRecyclerViewAdapter.OnItemClickListener<Order> {
            override fun onItemClick(item: Order, position: Int) {
                mActivity.startActivity<OrderDetailActivity>(ProviderConstant.KEY_ORDER_ID to item.id)
            }
        })
    }

    //确认收货、支付、取消订单
    private fun doOpt(optType: Int, order: Order) {
        when(optType) {
            OrderConstant.OPT_ORDER_CONFIRM -> {
                showConfirmDialog(order.id)
            }

            OrderConstant.OPT_ORDER_CANCEL -> {
                showCancelDialog(order.id)
            }

            OrderConstant.OPT_ORDER_PAY -> {
                ARouter.getInstance()
                    .build(RouterPath.PayCenter.PATH_PAY)
                    .withInt(ProviderConstant.KEY_ORDER_ID, order.id)
                    .withLong(ProviderConstant.KEY_ORDER_PRICE, order.totalPrice)
                    .navigation()

            }
        }
    }

    private fun loadData() {
        mPresenter.getOrderList(mOrderStatus)
    }

    //加载订单列表成功
    override fun onGetOrderListResult(result: MutableList<Order>?) {
        if (result != null && !result.isEmpty()) {
            mAdapter.setData(result)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        }else {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }

    /**
     * 显示确认收货痰弹窗
     */
    private fun showConfirmDialog(orderId: Int) {
        AlertView("确认收货", "确定已收到订单商品？", "取消", null, arrayOf("确定"),
            mActivity, AlertView.Style.Alert, OnItemClickListener { _, position ->
                if (position == 0) {
                    mPresenter.confirmOrder(orderId)
                }
            }
        ).setCancelable(true).show()
    }

    override fun onConfirmOrderResult(result: Boolean) {
        toast("确认收货成功")
        EventBus.getDefault().post(ConfirmOrderEvent())
    }

    /**
     * 显示取消订单痰弹窗
     */
    private fun showCancelDialog(orderId: Int) {
        AlertView("取消订单", "确认取消该订单？", "取消", null, arrayOf("确定"),
            mActivity, AlertView.Style.Alert, OnItemClickListener { _, position ->
                if (position == 0) {
                    mPresenter.cancelOrder(orderId)
                }
            }
        ).setCancelable(true).show()
    }

    override fun onCancelOrderResult(result: Boolean) {
        toast("取消订单成功")
        EventBus.getDefault().post(CancelOrderEvent())
    }

    /** 确认收货事件 */
    @Subscribe
    fun onEvent(event: ConfirmOrderEvent) {

        //确认收货会影响的订单有：全部、待收货、已完成
        if (mOrderStatus == OrderStatus.ORDER_ALL ||
            mOrderStatus == OrderStatus.ORDER_WAIT_CONFIRM ||
            mOrderStatus == OrderStatus.ORDER_COMPLETED) {

            loadData()

        }

    }

    /** 取消订单事件 */
    @Subscribe
    fun onEvent(event: CancelOrderEvent) {
        //取消订单会影响的订单有：全部、待支付、已取消
        if (mOrderStatus == OrderStatus.ORDER_ALL ||
            mOrderStatus == OrderStatus.ORDER_WAIT_PAY ||
            mOrderStatus == OrderStatus.ORDER_CANCELED) {

            loadData()

        }
    }

    /** 订单支付成功事件 */
    @Subscribe
    fun onEvent(event: PaySuccessEvent) {
        //支付成功影响的订单有： 全部、待支付、待收货
        if (mOrderStatus == OrderStatus.ORDER_ALL ||
            mOrderStatus == OrderStatus.ORDER_WAIT_PAY ||
            mOrderStatus == OrderStatus.ORDER_WAIT_CONFIRM) {
            loadData()
        }
    }

    private fun toast(msg: String) {
        mActivity.toast(msg)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
    }

}
