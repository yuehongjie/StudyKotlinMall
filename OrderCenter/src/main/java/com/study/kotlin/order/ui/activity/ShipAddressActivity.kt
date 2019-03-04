package com.study.kotlin.order.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.kennyc.view.MultiStateView
import com.study.kotlin.base.adapter.BaseRecyclerViewAdapter
import com.study.kotlin.base.ext.onClick
import com.study.kotlin.base.ui.activity.BaseMvpActivity
import com.study.kotlin.order.R
import com.study.kotlin.order.data.common.OrderConstant
import com.study.kotlin.order.data.protocol.ShipAddress
import com.study.kotlin.order.event.SelectAddressEvent
import com.study.kotlin.order.injection.component.DaggerShipAddressComponent
import com.study.kotlin.order.presenter.ShipAddressPresenter
import com.study.kotlin.order.presenter.view.ShipAddressView
import com.study.kotlin.order.ui.adapter.ShipAddressAdapter
import kotlinx.android.synthetic.main.activity_ship_adress.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * 收货地址页面
 */
class ShipAddressActivity : BaseMvpActivity<ShipAddressPresenter>(), ShipAddressView {


    private lateinit var mAdapter: ShipAddressAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ship_adress)

        initView()

    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    override fun injectComponent() {
        DaggerShipAddressComponent.builder()
            .activityComponent(activityComponent)
            .build()
            .inject(this)

        mPresenter.mView = this
    }

    private fun initView() {

        mAddressRv.layoutManager = LinearLayoutManager(this)
        mAdapter = ShipAddressAdapter(this)
        mAddressRv.adapter = mAdapter
        mAdapter.mOptClickListener = object: ShipAddressAdapter.OnOptClickListener {

            override fun onSetDefault(address: ShipAddress) {
                mPresenter.editShipAddress(address)
            }

            override fun onEdit(address: ShipAddress) {
                startActivity<ShipAddressEditActivity>(OrderConstant.KEY_SHIP_ADDRESS to address)
            }

            override fun onDelete(address: ShipAddress) {
                AlertView.Builder()
                    .setContext(this@ShipAddressActivity)
                    .setStyle(AlertView.Style.Alert)
                    .setTitle("删除")
                    .setMessage("确定删除该地址？")
                    .setCancelText("取消")
                    .setDestructive("确定")
                    .setOnItemClickListener { _, position ->
                        if (position == 0) {
                            mPresenter.deleteShipAddress(address.id)
                        }
                    }
                    .build()
                    .show()

            }

        }

        //从订单确认页进入选择收货地址
        if (intent.getBooleanExtra(OrderConstant.KEY_IS_SELECT_ADDRESS, false)) {
            //点击选择地址
            mAdapter.setOnItemClickListener(object: BaseRecyclerViewAdapter.OnItemClickListener<ShipAddress> {
                override fun onItemClick(item: ShipAddress, position: Int) {
                    EventBus.getDefault().post(SelectAddressEvent(item))
                    finish()
                }
            })
        }


        mAddAddressBtn.onClick {
            startActivity<ShipAddressEditActivity>()
        }
    }

    private fun loadData() {
        mPresenter.getShipAddressList()
    }

    override fun onGetShipAddressListResult(result: MutableList<ShipAddress>?) {

        if (result.isNullOrEmpty()) {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }else{
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
            mAdapter.setData(result)
        }

    }

    override fun onEditShipAddressResult(result: Boolean) {
        toast("设置默认成功")
        loadData()
    }

    override fun onDeleteShipAddressResult(result: Boolean) {
        toast("删除成功")
        loadData()
    }
}
