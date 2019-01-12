package com.study.kotlin.user.ui.activity

import android.os.Bundle
import android.view.View
import com.study.kotlin.base.ext.enable
import com.study.kotlin.base.ui.activity.BaseMvpActivity
import com.study.kotlin.user.R
import com.study.kotlin.user.injection.component.DaggerUserComponent
import com.study.kotlin.user.presenter.ForgetPwdPresenter
import com.study.kotlin.user.presenter.view.ForgetPwdView
import kotlinx.android.synthetic.main.activity_forget_pwd.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class ForgetPwdActivity: BaseMvpActivity<ForgetPwdPresenter>(), ForgetPwdView, View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_forget_pwd)

        initView()

    }

    //注册 dagger
    override fun injectComponent() {

        DaggerUserComponent.builder()
            .activityComponent(activityComponent)
            .build()
            .inject(this)

        mPresenter.mView = this

    }

    //初始化 view
    private fun initView() {

        //设置点击事件
        mNextBtn.setOnClickListener(this)
        mVerifyCodeBtn.setOnClickListener(this)

        //获取验证码按钮是否可用
        mVerifyCodeBtn.enable(mMobileEt){isVerifyBtnEnable()}

        //下一步按钮是否可用
        mNextBtn.enable(mVerifyCodeEt){isNextBtnEnable()}
        mNextBtn.enable(mMobileEt){isNextBtnEnable()}

    }


    //忘记密码 回调结果
    override fun forgetPwdResult(result: String) {

        toast(result)

    }


    /** 忘记密码按钮是否可用 */
    private fun isNextBtnEnable(): Boolean {

        return mMobileEt.text.isNullOrEmpty().not() &&
                mVerifyCodeEt.text.isNullOrEmpty().not()


    }

    /** 获取验证码的按钮是否可用 */
    private fun isVerifyBtnEnable(): Boolean {
        return mMobileEt.text.isNullOrEmpty().not()
    }

    //按钮点击
    override fun onClick(v: View) {

        when(v.id) {

            R.id.mNextBtn -> { mPresenter.forgetPwd(mMobileEt.text.toString(), mVerifyCodeEt.text.toString()) }

        }

    }

}