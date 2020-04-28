package com.ripple.media.picker.config

import java.io.File

/**
 * Author: fanyafeng
 * Data: 2020/4/21 18:39
 * Email: fanyafeng@live.cn
 * Description:
 */
interface IImagePickConfig : MediaPickConfig {

    companion object {
        const val IMAGE_CONFIG_NAME = "item_image_pick_config"

        const val TAKE_PICTURE_CODE = 518
    }

    /**
     * 用户拍照后存储的路径
     */
    fun getPhotoFile(): File

//    /**
//     * 用户裁剪后保存图片的路径
//     */
//    fun getCropFile()


    /**
     * 是否显示拍照按钮
     */
    fun showCamera():Boolean

//    fun setCropStyle()
//
//    enum class CropStyle {
//        CIRCLE,
//        SQUARE
//    }
}