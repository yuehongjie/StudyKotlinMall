package com.study.kotlin.order.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.base.utils.YuanFenConverter
import com.study.kotlin.base.adapter.BaseRecyclerViewAdapter
import com.study.kotlin.base.ext.loadUrl
import com.study.kotlin.base.ext.setVisible
import com.study.kotlin.order.R
import com.study.kotlin.order.data.protocol.Order
import kotlinx.android.synthetic.main.layout_order_item.view.*

/**
 * 订单列表的 item Holder
 */
class OrderAdapter(mContext: Context) : BaseRecyclerViewAdapter<Order, OrderAdapter.OrderHolder>(mContext) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_order_item, parent, false)
        return OrderHolder(view)
    }

    override fun onBindViewHolder(holder: OrderHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]
        //只有单个商品
        if (model.orderGoodsList.size == 1) {
            holder.itemView.mSingleGoodsView.setVisible(true)
            holder.itemView.mMultiGoodsView.setVisible(false)

            val goods = model.orderGoodsList.first()
            holder.itemView.mGoodsIconIv.loadUrl(goods.goodsIcon)
            holder.itemView.mGoodsDescTv.text = goods.goodsDesc
            holder.itemView.mGoodsPriceTv.text = "${YuanFenConverter.changeF2YWithUnit(goods.goodsPrice)}"
            holder.itemView.mGoodsCountTv.text = "x${goods.goodsCount}"
        }else {
            holder.itemView.mSingleGoodsView.setVisible(false)
            holder.itemView.mMultiGoodsView.setVisible(true)
            holder.itemView.mMultiGoodsView.removeAllViews()

            for (goods in model.orderGoodsList) {
                val imageView = LayoutInflater.from(mContext).inflate(R.layout.item_image_in_order, null) as ImageView
                imageView.loadUrl(goods.goodsIcon)
                holder.itemView.mMultiGoodsView.addView(imageView)
            }
        }

        holder.itemView.mOrderInfoTv.text = "合计${model.orderGoodsList.size}件商品，总价${YuanFenConverter.changeF2YWithUnit(model.totalPrice)}"
    }

    class OrderHolder(view: View): RecyclerView.ViewHolder(view)
}