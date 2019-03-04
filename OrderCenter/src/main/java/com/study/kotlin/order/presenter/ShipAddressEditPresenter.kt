package com.study.kotlin.order.presenter

import com.study.kotlin.base.ext.execute
import com.study.kotlin.base.presenter.BasePresenter
import com.study.kotlin.base.rx.BaseSubscriber
import com.study.kotlin.order.data.protocol.ShipAddress
import com.study.kotlin.order.presenter.view.ShipAddressEditView
import com.study.kotlin.order.service.ShipAddressService
import javax.inject.Inject

class ShipAddressEditPresenter @Inject constructor(): BasePresenter<ShipAddressEditView>() {

    @Inject
    lateinit var service: ShipAddressService

    fun addShipAddress(shipUserName: String, shipUserMobile: String, shipAddress: String) {

        if (!isNetWorkAvailable()) {
            return
        }

        mView.showLoading()

        service.addShipAddress(shipUserName, shipUserMobile, shipAddress)
            .execute(object: BaseSubscriber<Boolean>(mView) {

                override fun onNext(result: Boolean) {
                    mView.onAddShipAddressResult(result)
                }

            })
    }


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

}