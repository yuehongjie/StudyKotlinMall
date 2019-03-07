package com.study.kotlin.pay.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.study.kotlin.base.ui.activity.BaseActivity
import com.study.kotlin.pay.R
import com.study.kotlin.provider.common.ProviderConstant
import com.study.kotlin.provider.router.RouterPath
import org.jetbrains.anko.toast

@Route(path = RouterPath.PayCenter.PATH_PAY)
class CashRegisterActivity: BaseActivity() {

    @Autowired(name = ProviderConstant.KEY_ORDER_ID)
    @JvmField
    var mOrderId: Int = 0

    @Autowired(name = ProviderConstant.KEY_ORDER_PRICE)
    @JvmField
    var mTotalPrice: Long = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash_register)

        ARouter.getInstance().inject(this)

        toast("订单id: $mOrderId  价格：$mTotalPrice")
    }
}