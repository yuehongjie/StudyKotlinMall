package com.study.kotlin.mall.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.study.kotlin.user.utils.UserPrefsUtils
import com.study.kotlin.base.ext.onClick
import com.study.kotlin.base.ui.fragment.BaseFragment
import com.study.kotlin.base.utils.GlideUtils
import com.study.kotlin.mall.R
import com.study.kotlin.mall.ui.activity.SettingActivity
import com.study.kotlin.provider.common.isLogin
import com.study.kotlin.user.ui.activity.LoginActivity
import com.study.kotlin.user.ui.activity.UserInfoActivity
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
            R.id.mUserIconIv, R.id.mUserNameTv -> {
                if (isLogin()) {
                    activity?.startActivity<UserInfoActivity>()
                }else {
                    activity?.startActivity<LoginActivity>()
                }
            }

            R.id.mSettingTv -> {

                activity?.startActivity<SettingActivity>()

            }
        }

    }


}