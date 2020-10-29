package com.ripple.media.picker.image.extend

import android.app.Activity
import android.content.Context
import com.ripple.media.picker.config.CropImageConfig
import com.ripple.media.picker.config.MediaThemeConfig
import com.ripple.media.picker.image.RippleImagePick
import com.ripple.media.picker.image.listener.ImageCropListener
import com.ripple.media.picker.lambda.SuccessLambda
import java.io.File

/**
 * Author: fanyafeng
 * Date: 2020/10/22 09:14
 * Email: fanyafeng@live.cn
 * Description: 图片裁剪扩展方法
 *
 * 图片裁剪分为两种：
 * 第一种：直接塞进一张图片进行裁剪
 * 第二种：在图库中选择之后进入到裁剪页面进行裁剪
 *
 */


/**
 * 直接塞进一张图片进行裁剪
 */
@JvmOverloads
fun Context.imageCrop(
    localImagePath: String,
    cropImageConfig: CropImageConfig,
    themeConfig: MediaThemeConfig? = null,
    successLambda: SuccessLambda<File?>
) {
    RippleImagePick.getInstance()
        .imageCrop(
            this,
            imagePath = localImagePath,
            cropImageConfig = cropImageConfig,
            themeConfig = themeConfig
        )
    RippleImagePick.getInstance().imageCropListener = object : ImageCropListener {
        override fun onImageCropSaveResult(file: File?) {
            successLambda?.invoke(file)
        }
    }
}

@JvmOverloads
fun Activity.imageCrop(
    localImagePath: String,
    cropImageConfig: CropImageConfig,
    themeConfig: MediaThemeConfig? = null,
    successLambda: SuccessLambda<File?>
) {
    (this as Context).imageCrop(localImagePath, cropImageConfig, themeConfig, successLambda)
}

/**
 * 利用图库选择图片进行裁剪
 */
@JvmOverloads
fun Context.imageCrop(
    cropImageConfig: CropImageConfig,
    themeConfig: MediaThemeConfig? = null,
    successLambda: SuccessLambda<File?>
) {
    RippleImagePick.getInstance().imageCrop(this, cropImageConfig, themeConfig = themeConfig)
    RippleImagePick.getInstance().imageCropListener = object : ImageCropListener {
        override fun onImageCropSaveResult(file: File?) {
            successLambda?.invoke(file)
        }
    }
}

@JvmOverloads
fun Activity.imageCrop(
    cropImageConfig: CropImageConfig,
    themeConfig: MediaThemeConfig? = null,
    successLambda: SuccessLambda<File?>
) {
    (this as Context).imageCrop(cropImageConfig, themeConfig, successLambda)
}