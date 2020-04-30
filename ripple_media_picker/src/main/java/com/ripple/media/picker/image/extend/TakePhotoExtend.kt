package com.ripple.media.picker.image.extend

import android.app.Activity
import android.content.Context
import com.ripple.media.picker.image.RippleImagePick
import com.ripple.media.picker.image.listener.SelectImageListListener
import com.ripple.media.picker.image.listener.TakePhotoListener
import com.ripple.media.picker.lambda.SuccessLambda
import com.ripple.media.picker.model.RippleMediaModel
import java.io.File

/**
 * Author: fanyafeng
 * Data: 2020/4/30 16:25
 * Email: fanyafeng@live.cn
 * Description:
 */
fun Context.takePhoto(successLambda: SuccessLambda<File?>) {
    RippleImagePick.getInstance().takePhotoListener = object : TakePhotoListener {
        override fun onTakePhotoListener(file: File?) {
            successLambda?.invoke(file)
        }
    }
    RippleImagePick.getInstance().takePhoto(this)
}

fun Activity.takePhoto(successLambda: SuccessLambda<File?>) {
    (this as Context).takePhoto(successLambda)
}