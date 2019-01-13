package com.study.kotlin.user.ui.activity

import android.os.Bundle
import com.study.kotlin.base.ext.enable
import com.study.kotlin.base.ext.onClick
import com.study.kotlin.base.ui.activity.BaseMvpActivity
import com.study.kotlin.user.R
import com.study.kotlin.user.injection.component.DaggerUserComponent
import com.study.kotlin.user.presenter.ResetPwdPresenter
import com.study.kotlin.user.presenter.view.ResetPwdView
import kotlinx.android.synthetic.main.activity_reset_pwd.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class ResetPwdActivity:BaseMvpActivity<ResetPwdPresenter> (), ResetPwdView{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_reset_pwd)

        initView()

    }

    private fun initView() {

        mConfirmBtn.enable(mPwdConfirmEt) {isConfirmBtnEnable()}
        mConfirmBtn.enable(mPwdEt) {isConfirmBtnEnable()}

        //确定按钮
        mConfirmBtn.onClick {

            mPresenter.resetPwd(intent.getStringExtra("mobile"), mPwdEt.text.toString())

        }

    }


    //注册 dagger
    override fun injectComponent() {
        DaggerUserComponent.builder()
            .activityComponent(activityComponent)
            .build()
            .inject(this)

        mPresenter.mView = this
    }


    //重置密码结果
    override fun resetPwdResult(result: String) {

        toast(result)

        //重置密码成功，跳转回登录界面(singleTask 任务栈中只有一个实例)
        startActivity<LoginActivity>()

    }

    /** 判断确认按钮是否可点击 */
    private fun isConfirmBtnEnable(): Boolean {

        return mPwdEt.text.toString().isNullOrEmpty().not() &&
                mPwdConfirmEt.text.toString().isNullOrEmpty().not()

    }


}