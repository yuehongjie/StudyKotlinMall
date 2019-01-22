package com.study.kotlin.base.widgets

import android.content.Context
import android.widget.ImageView
import com.study.kotlin.base.utils.GlideUtils
import com.youth.banner.loader.ImageLoader

class BannerImageLoader: ImageLoader() {

    override fun displayImage(context: Context, path: Any, imageView: ImageView) {
        GlideUtils.loadImage(context, path.toString(), imageView)
    }

}