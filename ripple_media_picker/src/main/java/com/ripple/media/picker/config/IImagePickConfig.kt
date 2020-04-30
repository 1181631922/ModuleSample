package com.ripple.media.picker.config

import com.ripple.media.picker.RippleMediaPick
import com.ripple.media.picker.model.RippleMediaModel
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

        const val TAKE_PHOTO_PATH = "take_photo_path"

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
    fun showCamera(): Boolean

    /**
     * 传入已选中图片列表
     */
    fun getSelectList(): List<RippleMediaModel>

//    fun setCropStyle()
//
//    enum class CropStyle {
//        CIRCLE,
//        SQUARE
//    }
}