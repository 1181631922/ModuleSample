package com.ripple.media.picker.image.extend

import android.app.Activity
import android.content.Context
import com.ripple.media.picker.config.IPreviewImageConfig
import com.ripple.media.picker.config.impl.PreviewImageConfig
import com.ripple.media.picker.image.RippleImagePick
import com.ripple.media.picker.model.RippleMediaModel

/**
 * Author: fanyafeng
 * Data: 2020/4/30 15:26
 * Email: fanyafeng@live.cn
 * Description:
 */
fun Context.imagePreview(list: List<RippleMediaModel>) {
    val config = PreviewImageConfig
        .Builder()
        .setImageList(list)
        .setSelectModel(IPreviewImageConfig.PreviewModel.NORMAL)
        .build()
    RippleImagePick.getInstance().imagePreview(this, config)
}

fun Activity.imagePreview(list: List<RippleMediaModel>) {
    (this as Context).imagePreview(list)
}