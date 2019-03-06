package com.study.kotlin.order.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.kennyc.view.MultiStateView
import com.study.kotlin.base.ui.fragment.BaseMvpFragment
import com.study.kotlin.order.R
import com.study.kotlin.order.data.common.OrderConstant
import com.study.kotlin.order.data.protocol.Order
import com.study.kotlin.order.injection.component.DaggerOrderComponent
import com.study.kotlin.order.presenter.OrderListPresenter
import com.study.kotlin.order.presenter.view.OrderListView
import com.study.kotlin.order.ui.adapter.OnOptClickListener
import com.study.kotlin.order.ui.adapter.OrderAdapter
import kotlinx.android.synthetic.main.fragment_order.*
import org.jetbrains.anko.support.v4.toast

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
                toast("去支付")
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
        loadData()
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
        loadData()
    }

}
