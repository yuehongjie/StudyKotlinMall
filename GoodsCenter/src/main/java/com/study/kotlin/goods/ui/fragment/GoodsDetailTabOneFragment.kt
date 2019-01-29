package com.study.kotlin.goods.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.base.utils.YuanFenConverter
import com.study.kotlin.base.ext.onClick
import com.study.kotlin.base.ui.fragment.BaseFragment
import com.study.kotlin.base.ui.fragment.BaseMvpFragment
import com.study.kotlin.base.widgets.BannerImageLoader
import com.study.kotlin.goods.R
import com.study.kotlin.goods.data.common.GoodsConstant
import com.study.kotlin.goods.data.protocol.Goods
import com.study.kotlin.goods.event.TestEvent
import com.study.kotlin.goods.injection.component.DaggerGoodsComponent
import com.study.kotlin.goods.presenter.GoodsDetailPresenter
import com.study.kotlin.goods.presenter.view.GoodsDetailView
import com.study.kotlin.goods.widgets.GoodsSkuPopView
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_goods_detail_tab_one.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.support.v4.toast

class GoodsDetailTabOneFragment: BaseMvpFragment<GoodsDetailPresenter>(), GoodsDetailView {


    private lateinit var mSkuPop: GoodsSkuPopView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.fragment_goods_detail_tab_one, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
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
            toast("显示sku弹窗")
        }

    }

    private fun initPop() {

        mSkuPop = GoodsSkuPopView(activity!!)

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
    }

    private fun setPopData(result: Goods) {

        mSkuPop.setGoodsIcon(result.goodsDefaultIcon)
        mSkuPop.setGoodsCode(result.goodsCode)
        mSkuPop.setGoodsPrice(result.goodsDefaultPrice)

        mSkuPop.setSkuData(result.goodsSku)

    }

    override fun onDestroyView() {
        super.onDestroyView()



    }

}