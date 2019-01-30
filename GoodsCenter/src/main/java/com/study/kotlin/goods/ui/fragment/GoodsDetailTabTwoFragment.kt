package com.study.kotlin.goods.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.study.kotlin.base.ext.loadUrl
import com.study.kotlin.base.ui.fragment.BaseFragment
import com.study.kotlin.goods.R
import com.study.kotlin.goods.event.GoodsDetailImageEvent
import kotlinx.android.synthetic.main.fragment_goods_detail_tab_two.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class GoodsDetailTabTwoFragment: BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_goods_detail_tab_two, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        EventBus.getDefault().register(this)

    }


    @Subscribe
    fun onGoodsDetailImageEvent(event: GoodsDetailImageEvent) {

        // 设置ImageView宽度固定，其高度按比例缩放适应
        // 布局中设置了 adjustViewBounds="true" 该属性必须与 MaxHeight 或 MaxWidth 一起使用才能有效
        mGoodsDetailOneIv.maxWidth = mDetailImageRoot.width
        mGoodsDetailOneIv.maxHeight = mDetailImageRoot.width * 5
        mGoodsDetailTwoIv.maxWidth = mDetailImageRoot.width
        mGoodsDetailTwoIv.maxHeight = mDetailImageRoot.width * 5

        mGoodsDetailOneIv.loadUrl(event.imgOne)
        mGoodsDetailTwoIv.loadUrl(event.imgTwo)

    }



    override fun onDestroyView() {
        super.onDestroyView()

        EventBus.getDefault().unregister(this)
    }

}