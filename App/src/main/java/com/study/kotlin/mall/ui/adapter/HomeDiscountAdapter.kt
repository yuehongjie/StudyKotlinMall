package com.study.kotlin.mall.ui.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.study.kotlin.base.adapter.BaseRecyclerViewAdapter
import com.study.kotlin.base.utils.GlideUtils
import com.study.kotlin.mall.R
import kotlinx.android.synthetic.main.layout_home_discount_item.view.*

class HomeDiscountAdapter(val context: Context): BaseRecyclerViewAdapter<String, HomeDiscountAdapter.DiscountHolder>(context) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscountHolder {

        val itemView = LayoutInflater.from(mContext).inflate(R.layout.layout_home_discount_item, parent, false)
        return DiscountHolder(itemView)

    }

    override fun onBindViewHolder(holder: DiscountHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        //加载图片
        GlideUtils.loadImage(context, dataList[position], holder.itemView.mDiscountIv)
        //静态假数据
        holder.itemView.mDiscountAfterTv.text = "￥99.99"
        holder.itemView.mDiscountBeforeTv.text = "￥100.00"
    }


    class DiscountHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            //设置 TextView 划线
            itemView.mDiscountBeforeTv.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
            itemView.mDiscountBeforeTv.paint.isAntiAlias = true
        }

    }

}