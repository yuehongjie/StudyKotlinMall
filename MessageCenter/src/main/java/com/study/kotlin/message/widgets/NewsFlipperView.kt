package com.study.kotlin.message.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.ViewFlipper
import com.study.kotlin.message.R
import org.jetbrains.anko.dimen
import org.jetbrains.anko.find
import org.jetbrains.anko.px2sp

class NewsFlipperView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val mFlipperView: ViewFlipper

    init {
        //加载布局
        val rootView = View.inflate(context, R.layout.layout_news_flipper, null)

        //获取 FlipperView
        mFlipperView = rootView.find(R.id.mFlipperView)
        //设置进入动画
        mFlipperView.setInAnimation(context, R.anim.news_bottom_in)
        //设置退出动画
        mFlipperView.setOutAnimation(context, R.anim.news_bottom_out)

        this.addView(rootView)
    }

    /**
     * 构建公告布局
     */
    private fun buildNewsView(text: String): View {

        val newsTv = TextView(context)
        newsTv.text = text;
        newsTv.textSize = px2sp(dimen(R.dimen.text_small_size))
        newsTv.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        return newsTv

    }

    /**
     * 设置公告数据
     */
    fun setData(data: Array<String>) {

        for (text in data) {
            mFlipperView.addView(buildNewsView(text))
        }

        mFlipperView.startFlipping()

    }

}