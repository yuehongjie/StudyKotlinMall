package com.study.kotlin.goods.ui.fragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import com.kotlin.base.utils.YuanFenConverter
import com.study.kotlin.base.ext.onClick
import com.study.kotlin.base.ui.activity.BaseActivity
import com.study.kotlin.base.ui.fragment.BaseMvpFragment
import com.study.kotlin.base.widgets.BannerImageLoader
import com.study.kotlin.goods.R
import com.study.kotlin.goods.data.common.GoodsConstant
import com.study.kotlin.goods.data.protocol.Goods
import com.study.kotlin.goods.event.GoodsDetailImageEvent
import com.study.kotlin.goods.injection.component.DaggerGoodsComponent
import com.study.kotlin.goods.presenter.GoodsDetailPresenter
import com.study.kotlin.goods.presenter.view.GoodsDetailView
import com.study.kotlin.goods.widgets.GoodsSkuPopView
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_goods_detail_tab_one.*
import org.greenrobot.eventbus.EventBus

class GoodsDetailTabOneFragment: BaseMvpFragment<GoodsDetailPresenter>(), GoodsDetailView {


    private lateinit var mSkuPop: GoodsSkuPopView
    private lateinit var mAnimationStart: ScaleAnimation
    private lateinit var mAnimationEnd: ScaleAnimation

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.fragment_goods_detail_tab_one, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initAnim()
        initPop()
        loadData()
    }

    override fun injectComponent() {
        DaggerGoodsComponent.builder()
            .activityComponent(activityComponent)
            .build()
            .inject(this)

        mPresenter.mView = this
    }

    fun initView() {

        mGoodsDetailBanner.setImageLoader(BannerImageLoader())
        mGoodsDetailBanner.setBannerAnimation(Transformer.Accordion)
        mGoodsDetailBanner.setDelayTime(3000)
        //设置指示器位置（当banner模式中有指示器时）
        mGoodsDetailBanner.setIndicatorGravity(BannerConfig.RIGHT)

        mSkuView.onClick {

            mSkuPop.showAtLocation((mActivity as BaseActivity).rootContentView, Gravity.BOTTOM, 0,0)

            //整个页面的背景缩放动画
            (mActivity as BaseActivity).rootContentView.startAnimation(mAnimationStart)

            mGoodsDetailBanner.stopAutoPlay() //否则布局缩放后 还自动播放的话 图片会有闪动

        }

    }

    private fun initPop() {

        mSkuPop = GoodsSkuPopView(mActivity)
        mSkuPop.setOnDismissListener {
            mSkuSelectedTv.text = mSkuPop.getSelectSku() + GoodsConstant.SKU_SEPARATOR + mSkuPop.getSelectCount()

            //整个页面的背景缩放动画
            (mActivity as BaseActivity).rootContentView.startAnimation(mAnimationEnd)

            mGoodsDetailBanner.startAutoPlay()
        }

    }

    private fun loadData() {

       mPresenter.getGoodsDetail(mActivity.intent.getIntExtra(GoodsConstant.KEY_GOODS_ID, -1))

    }

    override fun onGetGoodsDetailResult(result: Goods) {

        mGoodsDetailBanner.setImages(result.goodsBanner.split(","))
        mGoodsDetailBanner.start()

        mGoodsDescTv.text = result.goodsDesc
        mGoodsPriceTv.text = YuanFenConverter.changeF2YWithUnit(result.goodsDefaultPrice)
        mSkuSelectedTv.text = result.goodsDefaultSku

        setPopData(result)

        //发送事件 给 详情页显示图片
        EventBus.getDefault().post(GoodsDetailImageEvent(result.goodsDetailOne, result.goodsDetailTwo))
    }

    private fun setPopData(result: Goods) {

        mSkuPop.setGoodsIcon(result.goodsDefaultIcon)
        mSkuPop.setGoodsCode(result.goodsCode)
        mSkuPop.setGoodsPrice(result.goodsDefaultPrice)

        mSkuPop.setSkuData(result.goodsSku)

    }

    /*
     初始化缩放动画
    */
    private fun initAnim() {
        mAnimationStart = ScaleAnimation(
            1f, 0.95f, 1f, 0.95f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        mAnimationStart.duration = 500
        mAnimationStart.fillAfter = true

        mAnimationEnd = ScaleAnimation(
            0.95f, 1f, 0.95f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        mAnimationEnd.duration = 500
        mAnimationEnd.fillAfter = true
    }

    override fun onDestroyView() {
        super.onDestroyView()



    }

}