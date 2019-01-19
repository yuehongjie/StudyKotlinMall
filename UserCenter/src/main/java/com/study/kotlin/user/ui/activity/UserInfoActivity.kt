package com.study.kotlin.user.ui.activity

import android.os.Bundle
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.study.kotlin.base.ext.onClick
import com.study.kotlin.base.ui.activity.BaseMvpActivity
import com.study.kotlin.user.R
import com.study.kotlin.user.presenter.view.UserInfoPresenter
import com.study.kotlin.user.presenter.view.UserInfoView
import kotlinx.android.synthetic.main.activity_user_info.*
import org.jetbrains.anko.toast

class UserInfoActivity : BaseMvpActivity<UserInfoPresenter>(), UserInfoView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_user_info)

        initView()
    }

    private fun initView() {

        mUserIconView.onClick {
            showAlertView()
        }

    }

    private fun showAlertView() {

        AlertView.Builder().setContext(this)
            .setStyle(AlertView.Style.ActionSheet)
            .setTitle("上传头像")
            .setCancelText("取消")
            .setDestructive("拍照", "相册")
            .setOnItemClickListener(object : OnItemClickListener{
                override fun onItemClick(o: Any?, position: Int) {
                    if (position == 0) {
                        toast("拍照")
                    }else{
                        toast("相册")
                    }
                }

            })
            .build()
            .setCancelable(true)
            .show()

    }



    override fun injectComponent() {

    }
}