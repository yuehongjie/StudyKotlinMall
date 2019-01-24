package com.study.kotlin.goods.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.study.kotlin.base.adapter.BaseRecyclerViewAdapter
import com.study.kotlin.base.utils.GlideUtils
import com.study.kotlin.goods.R
import com.study.kotlin.goods.data.protocol.Category
import kotlinx.android.synthetic.main.layout_second_category_item.view.*

/*
    二级商品分类Adapter
 */
class SecondCategoryAdapter(context: Context): BaseRecyclerViewAdapter<Category, SecondCategoryAdapter.ViewHolder>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext)
                .inflate(R.layout.layout_second_category_item,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]
        //分类图片
        GlideUtils.loadImage(mContext, model.categoryIcon, holder.itemView.mCategoryIconIv)
        //分类名称
        holder.itemView.mSecondCategoryNameTv.text = model.categoryName

    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)

}
