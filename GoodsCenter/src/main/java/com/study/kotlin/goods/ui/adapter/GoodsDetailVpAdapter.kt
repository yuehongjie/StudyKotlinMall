package com.study.kotlin.goods.ui.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.study.kotlin.goods.ui.fragment.GoodsDetailTabOneFragment
import com.study.kotlin.goods.ui.fragment.GoodsDetailTabTwoFragment

class GoodsDetailVpAdapter(fm: FragmentManager, context: Context): FragmentPagerAdapter(fm){


    private val titles = listOf("商品", "详情")

    override fun getItem(position: Int): Fragment {

        return if (position == 0) {

             GoodsDetailTabOneFragment()

        }else {

            GoodsDetailTabTwoFragment()
        }

    }

    override fun getCount(): Int {
        return titles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

}