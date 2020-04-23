package com.ripple.media.picker.image.extend

import android.app.Activity
import android.content.Context
import com.ripple.media.picker.RippleMediaPick
import com.ripple.media.picker.config.IImagePickConfig
import com.ripple.media.picker.config.impl.ImagePickConfig
import com.ripple.media.picker.image.RippleImagePick
import com.ripple.media.picker.image.listener.SelectImageListListener
import com.ripple.media.picker.lambda.SuccessLambda
import com.ripple.media.picker.model.RippleMediaModel

/**
 * Author: fanyafeng
 * Data: 2020/4/23 10:57
 * Email: fanyafeng@live.cn
 * Description:
 */
@JvmOverloads
fun Context.imagePick(
    config: IImagePickConfig = RippleMediaPick.getInstance().imagePickConfig,
    successLambda: SuccessLambda<List<RippleMediaModel>>
) {

    RippleImagePick.getInstance().selectImageListListener = object : SelectImageListListener {
        override fun selectImageList(imageList: List<RippleMediaModel>) {
            successLambda?.invoke(imageList)
        }
    }
    RippleImagePick.getInstance().imagePickForMulti(this, config = config)
}

@JvmOverloads
fun Activity.imagePick(
    config: IImagePickConfig = RippleMediaPick.getInstance().imagePickConfig,
    successLambda: SuccessLambda<List<RippleMediaModel>>
) {
    (this as Context).imagePick(config, successLambda)
}

fun Context.imagePick(count: Int, successLambda: SuccessLambda<List<RippleMediaModel>>) {
    val config = ImagePickConfig.Builder().setCount(count).build()
    imagePick(config, successLambda)
}


