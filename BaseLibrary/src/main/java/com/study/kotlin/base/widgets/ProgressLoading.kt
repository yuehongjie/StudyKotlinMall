package com.study.kotlin.base.widgets

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.view.Gravity
import android.widget.ImageView
import com.study.kotlin.base.R
import org.jetbrains.anko.find

/**
 * 加载弹窗 通过 create 方法来创建
 */
class ProgressLoading private constructor(context: Context, themeResId: Int) : Dialog(context, themeResId) {


    companion object {

        private lateinit var animDrawable:AnimationDrawable

        private lateinit var mDialog: ProgressLoading

        fun create(context: Context): ProgressLoading {

            mDialog = ProgressLoading(context, R.style.LightProgressDialog)
            //设置布局
            mDialog.setContentView(R.layout.progress_dialog)
            //设置可取消
            mDialog.setCancelable(true)
            //设置不能点击外部取消（但可以点返回键取消）
            mDialog.setCanceledOnTouchOutside(false)

            mDialog.window?.let {
                //设置居中
                //it.attributes.gravity = Gravity.CENTER

                //设置窗口透明度
                it.attributes.dimAmount = 0.2f

            }

            //获取动画背景
            val  loadingView = mDialog.find<ImageView>(R.id.iv_loading)
            animDrawable = loadingView.background as AnimationDrawable

            return mDialog


        }

    }

    fun showProgress() {

        mDialog.show()
        animDrawable.start()

    }

    fun hideProgress() {

        mDialog.dismiss()
        animDrawable.stop()

    }

}