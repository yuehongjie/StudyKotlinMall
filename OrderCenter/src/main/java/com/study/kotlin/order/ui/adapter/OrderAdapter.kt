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
import com.study.kotlin.base.ext.onClick
import com.study.kotlin.base.ext.setVisible
import com.study.kotlin.order.R
import com.study.kotlin.order.data.common.OrderConstant
import com.study.kotlin.order.data.common.OrderStatus
import com.study.kotlin.order.data.protocol.Order
import kotlinx.android.synthetic.main.layout_order_item.view.*
import org.jetbrains.anko.dip

/**
 * 订单列表的 item Holder
 */
class OrderAdapter(mContext: Context) : BaseRecyclerViewAdapter<Order, OrderAdapter.OrderHolder>(mContext) {

    var listener: OnOptClickListener? = null

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

        }else {//多个商品,不显示描述，动态设置多个商品图片

            holder.itemView.mSingleGoodsView.setVisible(false)
            holder.itemView.mMultiGoodsView.setVisible(true)
            holder.itemView.mMultiGoodsView.removeAllViews()

            for (goods in model.orderGoodsList) {
                val imageView = ImageView(mContext)
                val layoutParams = ViewGroup.MarginLayoutParams(mContext.dip(60.0f),mContext.dip(60.0f))
                layoutParams.marginEnd = mContext.dip(10.0f)
                imageView.layoutParams = layoutParams
                imageView.loadUrl(goods.goodsIcon)
                holder.itemView.mMultiGoodsView.addView(imageView)
            }

        }

        holder.itemView.mOrderInfoTv.text = "合计${model.orderGoodsList.size}件商品，总价${YuanFenConverter.changeF2YWithUnit(model.totalPrice)}"

        //根据订单状态设置颜色、文案及对应操作按钮
        when(model.orderStatus){
            OrderStatus.ORDER_WAIT_PAY -> {
                holder.itemView.mOrderStatusNameTv.text = "待支付"
                holder.itemView.mOrderStatusNameTv.setTextColor(mContext.resources.getColor(R.color.common_red))
                setOptVisible(false,true,true,holder)
            }
            OrderStatus.ORDER_WAIT_CONFIRM -> {
                holder.itemView.mOrderStatusNameTv.text = "待收货"
                holder.itemView.mOrderStatusNameTv.setTextColor(mContext.resources.getColor(R.color.common_blue))
                setOptVisible(true,false,true,holder)
            }

            OrderStatus.ORDER_COMPLETED -> {
                holder.itemView.mOrderStatusNameTv.text = "已完成"
                holder.itemView.mOrderStatusNameTv.setTextColor(mContext.resources.getColor(R.color.common_yellow))
                setOptVisible(false,false,false,holder)
            }

            OrderStatus.ORDER_CANCELED -> {
                holder.itemView.mOrderStatusNameTv.text = "已取消"
                holder.itemView.mOrderStatusNameTv.setTextColor(mContext.resources.getColor(R.color.common_gray))
                setOptVisible(false,false,false,holder)
            }
        }

        //设置操作按钮的点击事件
        //设置确认收货点击事件
        holder.itemView.mConfirmBtn.onClick {
            listener?.let {
                it.onOptClick(OrderConstant.OPT_ORDER_CONFIRM,model)
            }
        }

        //设置支付订单点击事件
        holder.itemView.mPayBtn.onClick {
            listener?.let {
                it.onOptClick(OrderConstant.OPT_ORDER_PAY,model)
            }
        }

        //设置取消订单点击事件
        holder.itemView.mCancelBtn.onClick {
            listener?.let {
                it.onOptClick(OrderConstant.OPT_ORDER_CANCEL,model)
            }
        }
    }

    /*
        设置操作按钮显示或隐藏
     */
    private fun setOptVisible(confirmVisible: Boolean, waitPayVisible: Boolean, cancelVisible: Boolean,holder: OrderHolder) {

        //return //test

        holder.itemView.mConfirmBtn.setVisible(confirmVisible)
        holder.itemView.mPayBtn.setVisible(waitPayVisible)
        holder.itemView.mCancelBtn.setVisible(cancelVisible)

        if (confirmVisible or waitPayVisible or cancelVisible){
            holder.itemView.mBottomView.setVisible(true)
        }else{
            holder.itemView.mBottomView.setVisible(false)
        }

    }

    class OrderHolder(view: View): RecyclerView.ViewHolder(view)
}

interface OnOptClickListener {
    /**
     * @param optType 操作的类型
     * @param order 当前订单
     */
    fun onOptClick(optType:Int,order:Order)
}