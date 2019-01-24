package com.study.kotlin.mall.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.study.kotlin.base.utils.GlideUtils
import com.study.kotlin.mall.R
import kotlinx.android.synthetic.main.layout_topic_item.view.*

/**
 * 首页话题数据
 */
class TopicAdapter(private val context: Context, private val list: List<String>): PagerAdapter() {


    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val rootView = LayoutInflater.from(context).inflate(R.layout.layout_topic_item,null)


        GlideUtils.loadImage(context, list[position], rootView.mTopicIv)

        container.addView(rootView)

        return rootView
    }


    override fun isViewFromObject(view: View, `object`: Any): Boolean {

        return view === `object`

    }

    override fun getCount(): Int {

        return list.size

    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)

        container.removeView(`object` as View)

    }
}