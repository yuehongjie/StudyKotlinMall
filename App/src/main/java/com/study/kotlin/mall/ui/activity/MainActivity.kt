package com.study.kotlin.mall.ui.activity


import android.os.Bundle
import com.study.kotlin.base.ui.activity.BaseActivity
import com.study.kotlin.mall.R
import com.study.kotlin.mall.ui.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        test()
    }

    private fun test() {

        //2秒后，测试显示购物车、消息
        Observable.timer(2, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                mBottomNavBar.checkCarBadgeCount(20)
                mBottomNavBar.checkMsgBadgeVisible(true)
            }


        //5秒后，测试隐藏购物车、消息
        Observable.timer(5, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                mBottomNavBar.checkCarBadgeCount(0)
                mBottomNavBar.checkMsgBadgeVisible(false)
            }

    }
    private fun initView() {

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.mContainer, HomeFragment())
        ft.commit()

    }

}
