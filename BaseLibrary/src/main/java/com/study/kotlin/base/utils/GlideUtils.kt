package com.study.kotlin.base.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.study.kotlin.base.R

/*
    Glide工具类
 */
object GlideUtils {


    fun loadImageCenterCrop(context: Context, url: String, placeholder: Int, imageView: ImageView) {

        val options = RequestOptions().centerCrop().placeholder(placeholder)

        Glide.with(context).load(url).apply(options).into(imageView)
    }

    fun loadImage(context: Context, url: String, imageView: ImageView) {

        //val options = RequestOptions().centerCrop()

        Glide.with(context).load(url).into(imageView)
    }

    fun loadImageFitCenter(context: Context, url: String, imageView: ImageView) {
        val options = RequestOptions().fitCenter()
        Glide.with(context).load(url).apply(options).into(imageView)
    }

    /*
     *  有加载失败和预览图片
     */
    fun loadUrlImage(context: Context, url: String, imageView: ImageView){

        val options = RequestOptions()
            .placeholder(R.drawable.default_loading)
            .error(R.drawable.default_loading)
            .centerCrop()

        Glide.with(context)
            .load(url)
            .apply(options)
            .transition(DrawableTransitionOptions.withCrossFade()) // 淡入淡出效果
            .into(imageView)
    }
}
