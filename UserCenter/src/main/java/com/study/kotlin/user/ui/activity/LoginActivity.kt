package com.study.kotlin.user.ui.activity

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.study.kotlin.user.utils.UserPrefsUtils
import com.study.kotlin.base.ext.enable
import com.study.kotlin.base.ui.activity.BaseMvpActivity
import com.study.kotlin.provider.PushProvider
import com.study.kotlin.provider.router.RouterPath
import com.study.kotlin.user.R
import com.study.kotlin.user.data.protocol.UserInfo
import com.study.kotlin.user.injection.component.DaggerUserComponent
import com.study.kotlin.user.presenter.LoginPresenter
import com.study.kotlin.user.presenter.view.LoginView
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

@Route(path = RouterPath.UserCenter.PATH_LOGIN)
class LoginActivity: BaseMvpActivity<LoginPresenter>(), LoginView, View.OnClickListener {

    //通过注解解耦，获取服务实例
    @Autowired(name = RouterPath.MessageCenter.PATH_MESSAGE_PUSH)
    @JvmField
    var mPushProvider: PushProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //注入 ARouter
        ARouter.getInstance().inject(this)

        setContentView(R.layout.activity_login)

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
        mLoginBtn.setOnClickListener(this)
        mForgetPwdTv.setOnClickListener(this)
        //布局中包含的子布局不能直接使用 id 引用
        mHeaderBar.getRightView().setOnClickListener(this)

        //登录按钮是否可用
        mLoginBtn.enable(mMobileEt){isLoginBtnEnable()}
        mLoginBtn.enable(mPwdEt){isLoginBtnEnable()}

    }


    /** 登录成功回调 */
    override fun loginResult(result: UserInfo) {

        toast("登录成功")
        UserPrefsUtils.putUserInfo(result)

        finish()

    }


    /** 登录按钮是否可用 */
    private fun isLoginBtnEnable(): Boolean {

        return mMobileEt.text.isNullOrEmpty().not() &&
                mPwdEt.text.isNullOrEmpty().not()


    }

    //按钮点击
    override fun onClick(v: View) {

        when(v.id) {

            R.id.mLoginBtn -> {

                mPresenter.login(mMobileEt.text.toString(), mPwdEt.text.toString(), mPushProvider?.getPushId() ?: "")

            }

            R.id.mRightTv -> { startActivity<RegisterActivity>() }

            R.id.mForgetPwdTv -> {startActivity<ForgetPwdActivity>()}

        }

    }

}