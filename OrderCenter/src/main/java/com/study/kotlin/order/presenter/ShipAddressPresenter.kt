package com.study.kotlin.order.presenter

import com.study.kotlin.base.ext.execute
import com.study.kotlin.base.presenter.BasePresenter
import com.study.kotlin.base.rx.BaseSubscriber
import com.study.kotlin.order.data.protocol.ShipAddress
import com.study.kotlin.order.presenter.view.ShipAddressView
import com.study.kotlin.order.service.ShipAddressService
import javax.inject.Inject

class ShipAddressPresenter @Inject constructor(): BasePresenter<ShipAddressView>() {

    @Inject
    lateinit var service: ShipAddressService

    /**
     * 获取地址列表
     */
    fun getShipAddressList() {

        if (!isNetWorkAvailable()) {
            return
        }

        mView.showLoading()

        service.getShipAddressList()
            .execute(object : BaseSubscriber<MutableList<ShipAddress>?> (mView) {

                override fun onNext(result: MutableList<ShipAddress>?) {
                    mView.onGetShipAddressListResult(result)
                }

            })

    }

    /**
     * 编辑地址
     */
    fun editShipAddress(address: ShipAddress) {

        if (!isNetWorkAvailable()) {
            return
        }

        mView.showLoading()

        service.editShipAddress(address)
            .execute(object: BaseSubscriber<Boolean>(mView) {
                override fun onNext(result: Boolean) {
                    mView.onEditShipAddressResult(result)
                }
            })
    }

    fun deleteShipAddress(id: Int) {
        if (!isNetWorkAvailable()) {
            return
        }

        mView.showLoading()

        service.deleteShipAddress(id)
            .execute(object: BaseSubscriber<Boolean>(mView) {
                override fun onNext(result: Boolean) {
                    mView.onDeleteShipAddressResult(result)
                }
            })
    }

}