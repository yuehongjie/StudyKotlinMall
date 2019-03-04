package com.study.kotlin.order.ui.activity

import android.os.Bundle
import com.study.kotlin.base.ext.onClick
import com.study.kotlin.base.ui.activity.BaseMvpActivity
import com.study.kotlin.order.R
import com.study.kotlin.order.data.common.OrderConstant
import com.study.kotlin.order.data.protocol.ShipAddress
import com.study.kotlin.order.injection.component.DaggerShipAddressComponent
import com.study.kotlin.order.presenter.ShipAddressEditPresenter
import com.study.kotlin.order.presenter.view.ShipAddressEditView
import kotlinx.android.synthetic.main.activity_ship_address_edit.*
import org.jetbrains.anko.toast

/**
 * 编辑收货地址
 */
class ShipAddressEditActivity : BaseMvpActivity<ShipAddressEditPresenter>(), ShipAddressEditView {

    private var mAddress: ShipAddress? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ship_address_edit)

        initView()
        initData()
    }

    override fun injectComponent() {
        DaggerShipAddressComponent.builder()
            .activityComponent(activityComponent)
            .build()
            .inject(this)

        mPresenter.mView = this
    }


    private fun initView() {
        mSaveBtn.onClick {
            if (mShipNameEt.text.isNullOrEmpty()) {
                toast("请输入收货人")
                return@onClick
            }
            if (mShipMobileEt.text.isNullOrEmpty()) {
                toast("请输入联系方式")
                return@onClick
            }

            if (mShipAddressEt.text.isNullOrEmpty()) {
                toast("请输入收货地址")
                return@onClick
            }

            if (mAddress == null) {
                //新增地址
                mPresenter.addShipAddress(
                    mShipNameEt.text.toString(),
                    mShipMobileEt.text.toString(),
                    mShipAddressEt.text.toString())
            }else {

                mAddress!!.shipUserName = mShipNameEt.text.toString()
                mAddress!!.shipUserMobile = mShipMobileEt.text.toString()
                mAddress!!.shipAddress = mShipAddressEt.text.toString()

                //修改地址
                mPresenter.editShipAddress(mAddress!!)
            }

        }
    }

    private fun initData() {

        mAddress = intent.getParcelableExtra(OrderConstant.KEY_SHIP_ADDRESS)
        mAddress?.let {
            mShipNameEt.setText(it.shipUserName)
            mShipMobileEt.setText(it.shipUserMobile)
            mShipAddressEt.setText(it.shipAddress)
            mHeaderBar.setTitle("修改地址")
        }


    }


    override fun onAddShipAddressResult(result: Boolean) {
        toast("添加地址成功")
        onBackPressed()
    }

    override fun onEditShipAddressResult(result: Boolean) {
        toast("修改地址成功")
        onBackPressed()
    }
}
