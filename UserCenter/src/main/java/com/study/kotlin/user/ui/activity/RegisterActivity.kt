package com.study.kotlin.user.ui.activity

import android.os.Bundle
import com.study.kotlin.base.ext.onClick
import com.study.kotlin.base.ui.activity.BaseMvpActivity
import com.study.kotlin.user.R
import com.study.kotlin.user.injection.component.DaggerUserComponent
import com.study.kotlin.user.presenter.RegisterPresenter
import com.study.kotlin.user.presenter.view.RegisterView
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast

class RegisterActivity : BaseMvpActivity<RegisterPresenter>(), RegisterView {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)



        mBtnRegister.onClick {

            mPresenter.register(mMobileEt.text.toString(), mVerifyCodeEt.text.toString(), mPwdEt.text.toString())

        }


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
    }

}
