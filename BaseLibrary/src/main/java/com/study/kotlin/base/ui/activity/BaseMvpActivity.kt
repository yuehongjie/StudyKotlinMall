package com.study.kotlin.base.ui.activity

import android.os.Bundle
import com.study.kotlin.base.common.BaseApplication
import com.study.kotlin.base.injection.component.ActivityComponent
import com.study.kotlin.base.injection.component.DaggerActivityComponent
import com.study.kotlin.base.injection.module.ActivityModule
import com.study.kotlin.base.presenter.BasePresenter
import com.study.kotlin.base.presenter.view.BaseView
import javax.inject.Inject

/**
 * 所有 MVP 架构的 Activity 的基类
 *
 * 持有 Presenter 的引用， mPresenter 是泛型，上限是 BasePresenter，实际类型由子类的类决定
 *
 * 同时实现 BaseView 接口
 *
 */
abstract class BaseMvpActivity<T: BasePresenter<*>> : BaseActivity(), BaseView {


    @Inject
    lateinit var mPresenter: T

    // 提供 Activity 的 Component
    lateinit var activityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivityInjection()
        injectComponent()

    }

    private fun initActivityInjection() {

        activityComponent = DaggerActivityComponent.builder()
            .appComponent((application as BaseApplication).appComponent)
            .activityModule(ActivityModule(this))
            .build()

    }

    /**
     * 注册 Component
     */
    abstract fun injectComponent()


    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onError() {

    }


}