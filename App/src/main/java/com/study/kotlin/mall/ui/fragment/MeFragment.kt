package com.study.kotlin.mall.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.study.kotlin.base.ext.onClick
import com.study.kotlin.base.ui.fragment.BaseFragment
import com.study.kotlin.base.utils.GlideUtils
import com.study.kotlin.mall.R
import com.study.kotlin.mall.ui.activity.SettingActivity
import com.study.kotlin.order.data.common.OrderConstant
import com.study.kotlin.order.data.common.OrderStatus
import com.study.kotlin.order.ui.activity.OrderActivity
import com.study.kotlin.order.ui.activity.ShipAddressActivity
import com.study.kotlin.provider.common.afterLogin
import com.study.kotlin.provider.common.isLogin
import com.study.kotlin.user.ui.activity.UserInfoActivity
import com.study.kotlin.user.utils.UserPrefsUtils
import kotlinx.android.synthetic.main.fragment_me.*
import org.jetbrains.anko.startActivity

class MeFragment: BaseFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_me, null)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    override fun onStart() {
        super.onStart()
        setUserInfo()
    }

    private fun initView() {

        mUserIconIv.onClick(this)
        mUserNameTv.onClick(this)
        mSettingTv.onClick (this)
        mAddressTv.onClick(this)
        //订单
        mWaitPayOrderTv.onClick(this)
        mWaitConfirmOrderTv.onClick(this)
        mCompleteOrderTv.onClick(this)
        mAllOrderTv.onClick(this)

    }

    private fun setUserInfo() {

        //未登录
        if (!isLogin()) {

            mUserIconIv.setImageResource(R.drawable.icon_default_user)
            mUserNameTv.text = getString(R.string.un_login_text)

        }else {

            val localSavedIcon = UserPrefsUtils.getLocalSavedIcon()
            GlideUtils.loadImageCenterCrop(context!!, localSavedIcon, R.drawable.icon_default_user, mUserIconIv)
            mUserNameTv.text = UserPrefsUtils.getUserName()

        }

    }

    override fun onClick(v: View?) {

        when(v?.id) {
            //头像、用户名
            R.id.mUserIconIv, R.id.mUserNameTv -> {
                afterLogin {
                    activity?.startActivity<UserInfoActivity>()
                }
            }

            //设置
            R.id.mSettingTv -> {
                activity?.startActivity<SettingActivity>()
            }

            //收货地址管理
            R.id.mAddressTv -> {
                afterLogin {
                    activity?.startActivity<ShipAddressActivity>()
                }

            }

            //待支付订单
            R.id.mWaitPayOrderTv -> {
                activity?.startActivity<OrderActivity>(OrderConstant.KEY_ORDER_STATUS to OrderStatus.ORDER_WAIT_PAY)
            }
            //待收货
            R.id.mWaitConfirmOrderTv -> {
                activity?.startActivity<OrderActivity>(OrderConstant.KEY_ORDER_STATUS to OrderStatus.ORDER_WAIT_CONFIRM)
            }
            //已完成
            R.id.mCompleteOrderTv -> {
                activity?.startActivity<OrderActivity>(OrderConstant.KEY_ORDER_STATUS to OrderStatus.ORDER_COMPLETED)
            }
            //全部订单
            R.id.mAllOrderTv -> {
                activity?.startActivity<OrderActivity>(OrderConstant.KEY_ORDER_STATUS to OrderStatus.ORDER_ALL)
            }
        }

    }


}