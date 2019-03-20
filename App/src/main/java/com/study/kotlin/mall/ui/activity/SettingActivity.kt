package com.study.kotlin.mall.ui.activity

import android.os.Bundle
import android.view.View
import com.study.kotlin.user.utils.UserPrefsUtils
import com.study.kotlin.base.ext.onClick
import com.study.kotlin.base.ui.activity.BaseActivity
import com.study.kotlin.mall.R
import com.study.kotlin.provider.common.isLogin
import com.study.kotlin.user.ui.activity.UserInfoActivity
import kotlinx.android.synthetic.main.activity_setting.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SettingActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_setting)

        initView()
        checkLogin()

    }

    private fun initView() {

        //个人信息
        mUserInfoTv.onClick {
            startActivity<UserInfoActivity>()
        }

        //用户协议
        mUserProtocolTv.onClick {
            toast("用户协议")
        }

        mFeedBackTv.onClick {
            toast("反馈意见")
        }

        mAboutTv.onClick {
            toast("关于我们")
        }

        //退出登录
        mLogoutBtn.onClick {

            //清除用户信息
            UserPrefsUtils.putUserInfo(null)

            toast("已退出登录")

            checkLogin()

        }

    }

    private fun checkLogin() {
        if (isLogin()) {
            mUserInfoTv.visibility = View.VISIBLE
            mLogoutBtn.visibility = View.VISIBLE
        }else {
            mUserInfoTv.visibility = View.GONE
            mLogoutBtn.visibility = View.INVISIBLE
        }
    }

}