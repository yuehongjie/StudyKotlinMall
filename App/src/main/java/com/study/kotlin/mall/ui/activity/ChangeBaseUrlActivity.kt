package com.study.kotlin.mall.ui.activity

import android.os.Bundle
import com.study.kotlin.base.data.net.RetrofitFactory
import com.study.kotlin.base.ext.enable
import com.study.kotlin.base.ext.onClick
import com.study.kotlin.base.ui.activity.BaseActivity
import com.study.kotlin.mall.R
import kotlinx.android.synthetic.main.activity_change_baseurl.*
import org.jetbrains.anko.toast


/**
 * 为了方便"演示"，允许动态修改 Retrofit 的 BaseUrl
 */
class ChangeBaseUrlActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_baseurl)

        initView()

    }


    private fun initView() {


        mBaseUrlEt.setText(RetrofitFactory.instance.getCurrentBaseUrl())
        mChangeBtn.enable(mBaseUrlEt) {
            mBaseUrlEt.text.toString().trim().isEmpty().not()
        }

        mChangeBtn.onClick {

            val newUrl = mBaseUrlEt.text.toString()
            if (!newUrl.endsWith('/')) {

                toast(" BaseUrl 必须以 / 结束")
                return@onClick
            }

            RetrofitFactory.instance.changeBaseUrl(newUrl)

            toast("BaseUrl 已修改")
            onBackPressed()

        }

    }

}