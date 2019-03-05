package com.study.kotlin.order.ui.adapter

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.study.kotlin.order.ui.fragment.OrderFragment

class OrderVpAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {

    private val titles = arrayOf("全部","待付款","待收货","已完成","已取消")

    override fun getItem(position: Int): OrderFragment {

        return OrderFragment.newInstance(position)
    }

    override fun getCount(): Int {
       return titles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}