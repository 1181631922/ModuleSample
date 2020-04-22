package com.fanyafeng.modules.mediapick.config

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ripple.media.picker.loadframe.impl.ImageLoadFrame
import com.ripple.media.picker.view.RippleImageView

/**
 * Author: fanyafeng
 * Data: 2020/4/22 13:43
 * Email: fanyafeng@live.cn
 * Description:
 */
class RippleImageLoadFrameImpl : ImageLoadFrame {
    override fun displayImage(
        context: Context,
        path: String,
        imageView: RippleImageView,
        width: Int,
        height: Int
    ) {
        val requestOptions = RequestOptions()
            .override(width, height)
        Glide.with(context)
            .load(path)
            .apply(requestOptions)
            .into(imageView)
    }

    override fun previewImage(
        context: Context,
        path: String,
        imageView: RippleImageView,
        width: Int,
        height: Int
    ) {
        val requestOptions = RequestOptions()
            .override(width, height)
        Glide.with(context)
            .load(path)
            .apply(requestOptions)
            .into(imageView)
    }

    override fun downloadImage(context: Context, sourcePath: String, targetPath: String) {
    }

    override fun clearMemoryCache(context: Context) {
    }

}