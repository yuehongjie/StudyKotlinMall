package com.study.kotlin.user.ui.activity

import android.os.Bundle
import android.view.View
import com.study.kotlin.base.ext.enable
import com.study.kotlin.base.ext.onClick
import com.study.kotlin.base.ui.activity.BaseMvpActivity
import com.study.kotlin.user.R
import com.study.kotlin.user.injection.component.DaggerUserComponent
import com.study.kotlin.user.presenter.RegisterPresenter
import com.study.kotlin.user.presenter.view.RegisterView
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class RegisterActivity : BaseMvpActivity<RegisterPresenter>(), RegisterView , View.OnClickListener{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initView()


    }

    private fun initView() {

        mRegisterBtn.onClick(this)
        mVerifyCodeBtn.onClick(this)

        //监听注册按钮是否可用
        mRegisterBtn.enable(mMobileEt){ isRegisterBtnEnable() } //参数 lambda 最后一个参数可以放在括号外
        mRegisterBtn.enable(mVerifyCodeEt){ isRegisterBtnEnable() }
        mRegisterBtn.enable(mPwdEt){ isRegisterBtnEnable() }
        mRegisterBtn.enable(mPwdConfirmEt){ isRegisterBtnEnable() }

        //监听获取验证码按钮是否可用
        mVerifyCodeBtn.enable(mMobileEt) {isVerifyBtnEnable()}

    }

    override fun injectComponent() {

        //注入 Dagger
        //DaggerUserComponent.create().inject(this)
        DaggerUserComponent.builder()
            .activityComponent(activityComponent)
            .build()
            .inject(this)

        //使用 dagger 初始化 mPresenter
        mPresenter.mView = this

    }

    //实现 RegisterView 方法
    override fun registerResult(result: String) {

        toast(result)

        //注册成功返回到登录页面(singleTask 任务栈中只有一个实例)
        startActivity<LoginActivity>()
    }


    /*
    private var lastPressedTime = 0L

    //双击返回键 退出 App
    override fun onBackPressed() {

        val time = System.currentTimeMillis()
        if (time - lastPressedTime > 2000) {

            toast("再按一次退出")
            lastPressedTime = time

            return
        }

        super.onBackPressed()
    }
    */



    //按钮点击
    override fun onClick(v: View) {

        when(v.id) {
            R.id.mRegisterBtn ->
                mPresenter.register(mMobileEt.text.toString(), mVerifyCodeEt.text.toString(), mPwdEt.text.toString())

            //R.id.mVerifyCodeBtn ->
        }

    }


    /**
     * 注册按钮是否可用
     */
    private fun isRegisterBtnEnable(): Boolean {
        //四个输入框都不为空的时候按钮可点击
        return mMobileEt.text.isNullOrEmpty().not() &&
                mVerifyCodeEt.text.isNullOrEmpty().not() &&
                mPwdEt.text.isNullOrEmpty().not() &&
                mPwdConfirmEt.text.isNullOrEmpty().not()
    }

    /** 获取验证码的按钮是否可用 */
    private fun isVerifyBtnEnable(): Boolean {
        return mMobileEt.text.isNullOrEmpty().not()
    }

}
