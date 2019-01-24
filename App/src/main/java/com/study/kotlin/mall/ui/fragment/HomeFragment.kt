package com.study.kotlin.mall.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.study.kotlin.base.ui.fragment.BaseFragment
import com.study.kotlin.base.widgets.BannerImageLoader
import com.study.kotlin.mall.R
import com.study.kotlin.mall.common.*
import com.study.kotlin.mall.ui.adapter.HomeDiscountAdapter
import com.study.kotlin.mall.ui.adapter.TopicAdapter
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_topic_item.*
import me.crosswall.lib.coverflow.CoverFlow

class HomeFragment: BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_home, null)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initBanner()
        initFlipperNews()
        initDiscount()
        initTopic()

    }

    private fun initView() {

    }

    private fun initBanner() {

        //设置图片加载器
        mHomeBanner.setImageLoader(BannerImageLoader())
        //设置图片集合
        mHomeBanner.setImages(listOf(HOME_BANNER_ONE, HOME_BANNER_TWO, HOME_BANNER_THREE, HOME_BANNER_FOUR, HOME_BANNER_FIVE))
        //设置切换动画
        mHomeBanner.setBannerAnimation(Transformer.DepthPage)
        //设置轮播间隔
        mHomeBanner.setDelayTime(3000)
        //设置指示器的位置
        mHomeBanner.setIndicatorGravity(BannerConfig.RIGHT)
        //banner 设置方法全部调用完毕时 最后调用
        mHomeBanner.start()

    }

    private fun initFlipperNews() {

        mNewsFlipper.setData(arrayOf("夏日炎炎，第一波福利还有30秒到达战场", "新用户立领1000元优惠券"))

    }

    private fun initDiscount() {

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL

        val discountAdapter  = HomeDiscountAdapter(activity!!)
        mHomeDiscountRv.layoutManager = layoutManager
        mHomeDiscountRv.adapter = discountAdapter
        discountAdapter.setData(mutableListOf(HOME_DISCOUNT_ONE, HOME_DISCOUNT_TWO, HOME_DISCOUNT_THREE, HOME_DISCOUNT_FOUR, HOME_DISCOUNT_FIVE))


    }

    private fun initTopic() {

        mTopicPager.adapter = TopicAdapter(context!!, listOf(HOME_TOPIC_ONE, HOME_TOPIC_TWO, HOME_TOPIC_THREE, HOME_TOPIC_FOUR, HOME_TOPIC_FIVE))
        mTopicPager.currentItem = 1
        mTopicPager.offscreenPageLimit = 5

        CoverFlow.Builder()
            .with(mTopicPager)
            .pagerMargin(-30f) //每页之间的间距
            .scale(0.3f)
            .spaceSize(0f) //好像也跟间距有关系
            .build()

    }

}