package com.study.kotlin.user.ui.activity

import android.os.Bundle
import com.study.kotlin.base.ui.activity.BaseMvpActivity
import com.study.kotlin.user.R
import com.study.kotlin.user.presenter.RegisterPresenter
import com.study.kotlin.user.presenter.view.RegisterView
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast

class RegisterActivity : BaseMvpActivity<RegisterPresenter>(), RegisterView {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //在使用之前初始化 Presenter 个 View，否则崩溃 lateinit property mPresenter has not been initialized
        mPresenter = RegisterPresenter()
        mPresenter.mView = this

        mBtnRegister.setOnClickListener {
            //Toast.makeText(this, "注册", Toast.LENGTH_SHORT).show()

            //使用 Anko
            //toast("注册")
            //startActivity<TestAnkoActivity>("id" to 100) // Context 的扩展
            //startActivity(intentFor<TestAnkoActivity>("id" to 111)) // Intent 的扩展

            mPresenter.register(mMobileEt.text.toString(), mVerifyCodeEt.text.toString(), mPwdEt.text.toString())

        }


    }

    //实现 RegisterView 方法
    override fun registerResult(result:Boolean) {
        toast("注册成功 $result")
    }

}
