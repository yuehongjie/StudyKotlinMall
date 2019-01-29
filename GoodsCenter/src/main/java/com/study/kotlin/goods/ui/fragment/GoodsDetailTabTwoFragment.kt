package com.study.kotlin.goods.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.study.kotlin.base.ui.fragment.BaseFragment
import com.study.kotlin.goods.R

class GoodsDetailTabTwoFragment: BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_goods_detail_tab_two, container, false)

    }

}