package com.study.kotlin.pay.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.alipay.sdk.app.EnvUtils
import com.alipay.sdk.app.PayTask
import com.kotlin.base.utils.YuanFenConverter
import com.study.kotlin.base.ext.onClick
import com.study.kotlin.base.ui.activity.BaseMvpActivity
import com.study.kotlin.pay.R
import com.study.kotlin.provider.event.PaySuccessEvent
import com.study.kotlin.pay.injection.component.DaggerPayComponent
import com.study.kotlin.pay.presenter.PayPresenter
import com.study.kotlin.pay.presenter.view.PayView
import com.study.kotlin.provider.common.ProviderConstant
import com.study.kotlin.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_cash_register.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

@Route(path = RouterPath.PayCenter.PATH_PAY)
class CashRegisterActivity: BaseMvpActivity<PayPresenter>(), PayView , View.OnClickListener{


    private var mOrderId: Int = 0

    private var mTotalPrice: Long = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash_register)

        //设置支付宝沙箱环境 //注意沙箱环境只能使用余额，不能使用余额宝、花呗等
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX)

        ARouter.getInstance().inject(this)

        initView()
        initData()
    }

    override fun injectComponent() {
        DaggerPayComponent.builder()
            .activityComponent(activityComponent)
            .build()
            .inject(this)

        mPresenter.mView = this

    }

    private fun initView() {
        mAlipayTypeTv.isSelected = true
        mAlipayTypeTv.onClick(this)
        mWeixinTypeTv.onClick(this)
        mBankCardTypeTv.onClick(this)
        mPayBtn.onClick(this)
    }

    private fun initData() {
        mOrderId = intent.getIntExtra(ProviderConstant.KEY_ORDER_ID, -1)
        mTotalPrice = intent.getLongExtra(ProviderConstant.KEY_ORDER_PRICE, -1)

        mTotalPriceTv.text = YuanFenConverter.changeF2YWithUnit(mTotalPrice)
    }

    //获取到预支付订单
    override fun onGetPaySignResult(result: String) {

        //kotlin 扩展，开启异步线程
        doAsync {

            //支付结果为一个 Map ，如 {resultStatus=6001, result=, memo=用户取消}
            val resultMap: Map<String, String> = PayTask(this@CashRegisterActivity).payV2(result, true)
            Log.e("支付",resultMap.toString())

            //切换到 UI 线程
            uiThread {

                when {
                    "9000" == resultMap["resultStatus"] -> {//成功

                        //同步支付结果到服务器
                        mPresenter.payOrder(mOrderId)

                    }
                    "6001" == resultMap["resultStatus"] -> {
                        toast("取消支付")
                    }
                    else -> {
                        toast(resultMap["memo"] ?: "支付失败")
                    }
                }

            }

        }

    }

    override fun onPayOrderResult(result: Boolean) {
        toast("支付成功")
        EventBus.getDefault().post(PaySuccessEvent())
        finish()
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.mAlipayTypeTv -> {updatePayType(true,false,false)}
            R.id.mWeixinTypeTv -> {updatePayType(false,true,false)}
            R.id.mBankCardTypeTv -> {updatePayType(false,false,true)}
            R.id.mPayBtn -> {
                mPresenter.getPaySign(mOrderId,mTotalPrice)
            }
        }
    }

    /*
        选择支付类型，UI变化
     */
    private fun updatePayType(isAliPay:Boolean,isWeixinPay:Boolean,isBankCardPay:Boolean){
        mAlipayTypeTv.isSelected = isAliPay
        mWeixinTypeTv.isSelected = isWeixinPay
        mBankCardTypeTv.isSelected = isBankCardPay
    }
}