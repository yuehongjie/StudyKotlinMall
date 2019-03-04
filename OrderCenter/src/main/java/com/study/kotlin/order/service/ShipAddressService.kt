package com.study.kotlin.order.service

import com.study.kotlin.base.data.net.RetrofitFactory
import com.study.kotlin.base.ext.convert
import com.study.kotlin.base.ext.convertBoolean
import com.study.kotlin.order.data.api.ShipAddressApi
import com.study.kotlin.order.data.protocol.ShipAddress
import com.study.kotlin.order.data.req.AddShipAddressReq
import com.study.kotlin.order.data.req.DeleteShipAddressReq
import com.study.kotlin.order.data.req.EditShipAddressReq
import rx.Observable

class ShipAddressService {

    fun addShipAddress(shipUserName: String, shipUserMobile: String, shipAddress: String): Observable<Boolean> {
        return RetrofitFactory.instance.create(ShipAddressApi::class.java)
            .addShipAddress(AddShipAddressReq(shipUserName, shipUserMobile, shipAddress))
            .convertBoolean()
    }

    fun getShipAddressList():Observable<MutableList<ShipAddress>?> {
        return RetrofitFactory.instance.create(ShipAddressApi::class.java)
            .getShipAddressList()
            .convert()
    }

    fun editShipAddress(address: ShipAddress): Observable<Boolean> {

        return RetrofitFactory.instance.create(ShipAddressApi::class.java)
            .editShipAddress(EditShipAddressReq(address.id, address.shipUserName, address.shipUserMobile, address.shipAddress, address.shipIsDefault))
            .convertBoolean()

    }

    fun deleteShipAddress(id: Int): Observable<Boolean> {
        return RetrofitFactory.instance.create(ShipAddressApi::class.java)
            .deleteShipAddress(DeleteShipAddressReq(id))
            .convertBoolean()
    }
}