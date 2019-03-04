package com.study.kotlin.order.data.req

/*
    修改收货地址
 */
data class EditShipAddressReq(val id:Int,val shipUserName:String,val shipUserMobile:String,val shipAddress:String,val shipIsDefault:Int)
