package com.ripple.media.picker.loadframe.impl

import android.content.Context
import com.ripple.media.picker.loadframe.MediaLoadFrame
import com.ripple.media.picker.view.RippleImageView

/**
 * Author: fanyafeng
 * Data: 2020/4/22 09:56
 * Email: fanyafeng@live.cn
 * Description:
 */
interface ImageLoadFrame : MediaLoadFrame {
    /**
     * 显示图片
     */
    fun displayImage(
        context: Context,
        path: String,
        imageView: RippleImageView,
        width: Int,
        height: Int
    )

    /**
     * 预览图片
     */
    fun previewImage(
        context: Context,
        path: String,
        imageView: RippleImageView,
        width: Int,
        height: Int
    )

    /**
     * 下载图片
     */
    fun downloadImage(context: Context, sourcePath: String, targetPath: String)


}