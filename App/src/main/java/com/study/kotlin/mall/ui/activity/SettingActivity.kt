package com.study.kotlin.mall.ui.activity

import android.os.Bundle
import android.view.View
import com.study.kotlin.user.utils.UserPrefsUtils
import com.study.kotlin.base.ext.onClick
import com.study.kotlin.base.ui.activity.BaseActivity
import com.study.kotlin.mall.R
import com.study.kotlin.provider.common.isLogin
import kotlinx.android.synthetic.main.activity_setting.*
import org.jetbrains.anko.toast

class SettingActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_setting)

        initView()
        checkLogin()

    }

    private fun initView() {

        mLogoutBtn.onClick {

            //清除用户信息
            UserPrefsUtils.putUserInfo(null)

            toast("已退出登录")

            checkLogin()

        }

    }

    private fun checkLogin() {
        if (isLogin()) {
            mLogoutBtn.visibility = View.VISIBLE
        }else {
            mLogoutBtn.visibility = View.INVISIBLE
        }
    }

}